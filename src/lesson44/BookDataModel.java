package lesson44;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookDataModel {
    private static final String DATA_FILE = "data/json/db.json";
    private List<Book> books;
    private List<Employee> employees;
    private final Gson gson;

    public BookDataModel() {
        gson = new GsonBuilder().setPrettyPrinting().create();
        loadDataFromJson();

        if (books == null || books.isEmpty()) {
            books = new ArrayList<>();
            books.add(new Book("1", "Война и мир", "Лев Толстой", "/images/book1.jpg"));
            books.add(new Book("2", "Преступление и наказание", "Федор Достоевский", "/images/book2.jpg"));
            books.add(new Book("3", "Кладбище домашних животных", "Стивен Кинг", "/images/book3.jpeg"));
            books.add(new Book("4", "Убийство в Восточном экспрессе", "Агата Кристи", "/images/book4.jpeg"));
            books.add(new Book("5", "Вилла 'Белый конь'", "Агата Кристи", "/images/book5.jpeg"));
            books.add(new Book("6", "Десять негритят", "Агата Кристи", "/images/book6.jpeg"));
        }

        if (employees == null || employees.isEmpty()) {
            employees = new ArrayList<>();
            employees.add(new Employee("1", "Иван Иванов", "ivan@mail.com","password1"));
            employees.add(new Employee("2", "Петр Петров", "petr@mail.com","password2"));
        }
    }

    private void loadDataFromJson() {
        try (Reader reader = new FileReader(DATA_FILE)) {
            Type type = new TypeToken<Map<String, Object>>(){}.getType();
            Map<String, Object> data = gson.fromJson(reader, type);

            if (data == null) {
                initializeTestData();
                return;
            }

            try {
                Type bookListType = new TypeToken<List<Book>>(){}.getType();
                Type employeeListType = new TypeToken<List<Employee>>(){}.getType();

                Object booksObj = data.get("books");
                Object employeesObj = data.get("employees");

                if (booksObj == null || employeesObj == null) {
                    throw new IllegalStateException("Отсутствуют необходимые данные в JSON");
                }

                books = gson.fromJson(gson.toJson(booksObj), bookListType);
                employees = gson.fromJson(gson.toJson(employeesObj), employeeListType);

                validateData();

            } catch (Exception e) {
                initializeTestData();
            }
        } catch (IOException e) {
            initializeTestData();
        }
    }

    private void validateData() {
        if (books != null) {
            books.removeIf(book ->
                    book.getId() == null ||
                            book.getTitle() == null ||
                            book.getAuthor() == null
            );
        }

        if (employees != null) {
            employees.removeIf(employee ->
                    employee.getId() == null ||
                            employee.getName() == null ||
                            employee.getEmail() == null
            );
        }
    }

    private void initializeTestData() {
        books = new ArrayList<>();
        books.add(new Book("1", "Война и мир", "Лев Толстой", "/images/book1.jpg"));
        books.add(new Book("2", "Преступление и наказание", "Федор Достоевский", "/images/book2.jpg"));
        books.add(new Book("3", "Кладбище домашних животных", "Стивен Кинг", "/images/book3.jpeg"));
        books.add(new Book("4", "Убийство в Восточном экспрессе", "Агата Кристи", "/images/book4.jpeg"));
        books.add(new Book("5", "Вилла 'Белый конь'", "Агата Кристи", "/images/book5.jpeg"));
        books.add(new Book("6", "Десять негритят", "Агата Кристи", "/images/book6.jpeg"));

        employees = new ArrayList<>();
        employees.add(new Employee("1", "Иван Иванов", "ivan@mail.com", "password1"));
        employees.add(new Employee("2", "Петр Петров", "petr@mail.com", "password2"));

        saveDataToJson();
    }

    private void saveDataToJson() {
        try (Writer writer = new FileWriter(DATA_FILE)) {
            Map<String, Object> data = Map.of(
                    "books", books,
                    "employees", employees
            );
            gson.toJson(data, writer);
        } catch (IOException e) {
        }
    }

    public List<Book> getBooks() {
        return books;
    }

    public Book getBookById(String id) {
        Book found = books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElse(null);
        return found;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public Employee findEmployeeByEmail(String email) {
        return employees.stream()
                .filter(emp -> emp.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    public void addBook(Book book) {
        books.add(book);
        saveDataToJson();
    }

    public void updateBook(Book book) {
        int index = -1;
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId().equals(book.getId())) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            books.set(index, book);
            saveDataToJson();
        }
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
        saveDataToJson();
    }

    public void updateEmployee(Employee employee) {
        int index = -1;
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getEmail().equals(employee.getEmail())) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            employees.set(index, employee);
            saveDataToJson();
        }
    }
    public boolean employeeExistsByEmail(String email) {
        return employees.stream()
                .anyMatch(emp -> emp.getEmail().equals(email));
    }
    public Employee registerEmployee(String name, String email, String password) {
        if (employeeExistsByEmail(email)) {
            return null;
        }

        String id = String.valueOf(employees.size() + 1);

        Employee newEmployee = new Employee(id, name, email, password);

        employees.add(newEmployee);
        saveDataToJson();

        return newEmployee;
    }
    public Employee getEmployeeById(String id) {
        return employees.stream()
                .filter(emp -> emp.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}