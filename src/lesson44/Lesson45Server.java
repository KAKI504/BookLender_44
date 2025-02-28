package lesson44;
import com.sun.net.httpserver.HttpExchange;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Lesson45Server extends Lesson44Server {
    public Lesson45Server(String host, int port) throws IOException {
        super(host, port);

        registerGet("/registration", this::handleRegistrationPage);
        System.out.println("- Зарегистрирован маршрут: /registration (GET)");

        registerPost("/registration", this::handleRegistration);
        System.out.println("- Зарегистрирован маршрут: /registration (POST)");
    }

    private void handleRegistrationPage(HttpExchange exchange) {
        Map<String, Object> data = new HashMap<>();
        renderTemplate(exchange, "registration.ftlh", data);
    }

    private void handleRegistration(HttpExchange exchange) {
        String raw = getBody(exchange);
        Map<String, String> formData = parseFormData(raw);

        String email = formData.get("email");
        String name = formData.get("name");
        String password = formData.get("password");

        if (email == null || name == null || password == null) {
            Map<String, Object> data = new HashMap<>();
            data.put("error", "Все поля должны быть заполнены");
            renderTemplate(exchange, "registration.ftlh", data);
            return;
        }

        if (bookDataModel.employeeExistsByEmail(email)) {
            Map<String, Object> data = new HashMap<>();
            data.put("error", "Пользователь с таким email уже существует");
            renderTemplate(exchange, "registration.ftlh", data);
            return;
        }

        Employee newEmployee = bookDataModel.registerEmployee(name, email, password);

        Map<String, Object> data = new HashMap<>();
        data.put("success", "Регистрация выполнена успешно. Теперь вы можете войти.");
        renderTemplate(exchange, "registration.ftlh", data);
    }

    protected String getBody(HttpExchange exchange) {
        InputStream input = exchange.getRequestBody();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(input, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining(""));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
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
            data.put("error", "Неверный email или пароль");
            renderTemplate(exchange, "login.ftlh", data);

        } catch (IOException e) {
            redirect303(exchange, "/login");
        }
    }
}