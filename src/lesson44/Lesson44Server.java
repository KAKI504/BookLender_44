package lesson44;

import com.sun.net.httpserver.HttpExchange;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import server.BasicServer;
import server.ContentType;
import server.ResponseCodes;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lesson44Server extends BasicServer {
    private final static Configuration freemarker = initFreeMarker();
    protected final BookDataModel bookDataModel = new BookDataModel();
    protected Employee currentEmployee = null;

    public Lesson44Server(String host, int port) throws IOException {
        super(host, port);

        System.out.println("Регистрирую маршруты:");

        registerGet("/", this::handleRoot);
        System.out.println("- Зарегистрирован маршрут: /");

        registerGet("/books", this::handleBooks);
        System.out.println("- Зарегистрирован маршрут: /books");

        for (int i = 1; i <= 6; i++) {
            registerGet("/book/" + i, this::handleBookDetails);
            System.out.println("- Зарегистрирован конкретный маршрут: /book/" + i);

            registerGet("/borrow-book/" + i, this::handleBorrowBook);
            System.out.println("- Зарегистрирован конкретный маршрут: /borrow-book/" + i);

            registerGet("/return-book/" + i, this::handleReturnBook);
            System.out.println("- Зарегистрирован конкретный маршрут: /return-book/" + i);
        }

        registerGet("/login", this::handleLoginPage);
        System.out.println("- Зарегистрирован маршрут: /login");

        registerGet("/profile", this::handleProfile);
        System.out.println("- Зарегистрирован маршрут: /profile");

        registerPost("/login", this::handleLogin);
        System.out.println("- Зарегистрирован маршрут: POST /login");

        registerFileHandler(".css", ContentType.TEXT_CSS);
        registerFileHandler(".html", ContentType.TEXT_HTML);
        registerFileHandler(".jpg", ContentType.IMAGE_JPEG);
        registerFileHandler(".jpeg", ContentType.IMAGE_JPEG);
        registerFileHandler(".png", ContentType.IMAGE_PNG);
        System.out.println("- Зарегистрированы обработчики файлов: .css, .html, .jpg, .jpeg, .png");
        checkTemplateFiles();
        registerStaticResourcesHandler();
    }

    protected void handleRoot(HttpExchange exchange) {
        if (currentEmployee != null) {
            redirect303(exchange, "/profile");
        } else {
            renderTemplate(exchange, "login.ftlh", createDataModel());
        }
    }

    protected void handleLoginPage(HttpExchange exchange) {
        renderTemplate(exchange, "login.ftlh", createDataModel());
    }

    protected void handleLogin(HttpExchange exchange) {
        try {
            String raw = new String(exchange.getRequestBody().readAllBytes());
            Map<String, String> formData = parseFormData(raw);

            String email = formData.get("email");
            String password = formData.get("password");

            Employee employee = bookDataModel.findEmployeeByEmail(email);
            if (employee != null && password != null && password.equals(employee.getPassword())) {
                currentEmployee = employee;
                redirect303(exchange, "/profile");
                return;
            }

            Map<String, Object> data = createDataModel();
            data.put("error", "Пользователь не существует или неверный пароль");
            renderTemplate(exchange, "login.ftlh", data);

        } catch (IOException e) {
            redirect303(exchange, "/login");
        }
    }

    protected void handleProfile(HttpExchange exchange) {
        if (currentEmployee == null) {
            redirect303(exchange, "/login");
            return;
        }
        renderTemplate(exchange, "employee-profile.ftlh", createDataModel());
    }

    protected void handleBorrowBook(HttpExchange exchange) {
        if (currentEmployee == null) {
            redirect303(exchange, "/login");
            return;
        }

        try {
            String path = exchange.getRequestURI().getPath();
            String bookId = path.substring(path.lastIndexOf("/") + 1);
            Book book = bookDataModel.getBookById(bookId);

            Map<String, Object> data = createDataModel();

            if (book == null) {
                data.put("error", "Книга не найдена");
            } else if (book.isBorrowed()) {
                data.put("error", "Книга уже взята другим читателем");
            } else if (!currentEmployee.canBorrowBooks()) {
                data.put("error", "Вы не можете взять больше книг");
            } else {
                currentEmployee.borrowBook(book);
                bookDataModel.updateEmployee(currentEmployee);
                bookDataModel.updateBook(book);
                data.put("success", "Книга успешно взята");
            }

            renderTemplate(exchange, "books.ftlh", data);
        } catch (Exception e) {
            respond404(exchange);
        }

    }

    protected void handleReturnBook(HttpExchange exchange) {
        if (currentEmployee == null) {
            redirect303(exchange, "/login");
            return;
        }
        try {
            String path = exchange.getRequestURI().getPath();
            String bookId = path.substring(path.lastIndexOf("/") + 1);
            Book book = bookDataModel.getBookById(bookId);

            Map<String, Object> data = createDataModel();

            if (book == null) {
                data.put("error", "Книга не найдена (ID: " + bookId + ")");
            } else if (!book.isBorrowed()) {
                data.put("error", "Книга не была взята (Статус: " + book.getStatus() + ")");
            } else if (!currentEmployee.hasBorrowedBook(book)) {
                data.put("error", "Вы не брали эту книгу. Взята пользователем: " + book.getBorrowedBy());
            } else {
                System.out.println("Возврат книги: " + book.getTitle() + " от пользователя " + currentEmployee.getName());

                currentEmployee.returnBook(book);
                bookDataModel.updateEmployee(currentEmployee);
                bookDataModel.updateBook(book);
                data.put("success", "Книга успешно возвращена: " + book.getTitle());
            }

            renderTemplate(exchange, "books.ftlh", data);
        } catch (Exception e) {
            e.printStackTrace();
            respond404(exchange);
        }
    }

    protected void handleBooks(HttpExchange exchange) {
        renderTemplate(exchange, "books.ftlh", createDataModel());
    }

    protected void handleBookDetails(HttpExchange exchange) {
        if (currentEmployee == null) {
            redirect303(exchange, "/login");
            return;
        }

        try {
            String path = exchange.getRequestURI().getPath();
            String id = path.substring(path.lastIndexOf("/") + 1);
            Book book = bookDataModel.getBookById(id);

            if (book == null) {
                respond404(exchange);
                return;
            }

            Map<String, Object> data = createDataModel();
            data.put("book", book);

            renderTemplate(exchange, "book-details.ftlh", data);
        } catch (Exception e) {
            respond404(exchange);
        }
    }

    protected Map<String, Object> createDataModel() {
        Map<String, Object> data = new HashMap<>();
        data.put("employee", currentEmployee);
        data.put("books", bookDataModel.getBooks());
        return data;
    }

    protected Map<String, String> parseFormData(String raw) {
        Map<String, String> result = new HashMap<>();
        String[] pairs = raw.split("&");

        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                try {
                    String key = java.net.URLDecoder.decode(keyValue[0], "UTF-8");
                    String value = java.net.URLDecoder.decode(keyValue[1], "UTF-8");
                    result.put(key, value);
                } catch (UnsupportedEncodingException e) {
                }
            }
        }
        return result;
    }

    private static Configuration initFreeMarker() {
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
            String dataDir = new File("data").getAbsolutePath();
            File dir = new File(dataDir);
            if (!dir.exists()) {
                boolean created = dir.mkdirs();
                if (!created) {
                    throw new RuntimeException("Не удалось создать директорию: " + dataDir);
                }
            }
            cfg.setDirectoryForTemplateLoading(dir);
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            cfg.setWrapUncheckedExceptions(true);
            cfg.setFallbackOnNullLoopVariable(false);
            return cfg;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void freemarkerSampleHandler(HttpExchange exchange) {
        renderTemplate(exchange, "sample.ftlh", getSampleDataModel());
    }

    protected void renderTemplate(HttpExchange exchange, String templateFile, Object dataModel) {
        try {
            Template temp = freemarker.getTemplate(templateFile);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            try (OutputStreamWriter writer = new OutputStreamWriter(stream)) {
                temp.process(dataModel, writer);
                writer.flush();
                byte[] data = stream.toByteArray();
                sendByteData(exchange, ResponseCodes.OK, ContentType.TEXT_HTML, data);
            }
        } catch (IOException | TemplateException e) {
            respond404(exchange);
        }
    }

    private Map<String, Object> getSampleDataModel() {
        Map<String, Object> data = new HashMap<>();
        data.put("user", new SampleDataModel.User("Alex", "Attractor"));
        data.put("currentDateTime", LocalDateTime.now());
        data.put("customers", List.of(
                new SampleDataModel.User("Marco"),
                new SampleDataModel.User("Winston", "Duarte"),
                new SampleDataModel.User("Amos", "Burton", "'Timmy'")
        ));
        return data;
    }

    protected void redirect303(HttpExchange exchange, String path) {
        try {
            exchange.getResponseHeaders().add("Location", path);
            exchange.sendResponseHeaders(303, 0);
            exchange.getResponseBody().close();
        } catch (IOException e) {
        }
    }

    protected void checkTemplateFiles() {
        String dataDir = new File("data").getAbsolutePath();
        System.out.println("Проверяю наличие шаблонов в директории: " + dataDir);

        String[] templates = {
                "login.ftlh",
                "books.ftlh",
                "book-details.ftlh",
                "employee-profile.ftlh"
        };

        for (String template : templates) {
            File file = new File(dataDir, template);
            System.out.println("Шаблон " + template + ": " +
                    (file.exists() ? "СУЩЕСТВУЕТ" : "НЕ СУЩЕСТВУЕТ"));
        }
    }
}