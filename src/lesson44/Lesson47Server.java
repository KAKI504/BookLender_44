package lesson44;


import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Map;

public class Lesson47Server extends Lesson46Server {

    public Lesson47Server(String host, int port) throws IOException {
        super(host, port);

        registerGet("/employees", this::handleEmployeesList);

        registerGet("/employee/\\d+", this::handleEmployeeDetails);
    }

    protected void handleEmployeesList(HttpExchange exchange) {
        if (!isAuthenticated(exchange)) {
            redirect303(exchange, "/login");
            return;
        }

        Map<String, Object> data = createDataModel();
        data.put("allEmployees", bookDataModel.getEmployees());

        renderTemplate(exchange, "employees-list.ftlh", data);
    }

    protected void handleEmployeeDetails(HttpExchange exchange) {
        if (!isAuthenticated(exchange)) {
            redirect303(exchange, "/login");
            return;
        }

        try {
            String path = exchange.getRequestURI().getPath();
            String id = path.substring(path.lastIndexOf("/") + 1);

            Employee employeeDetails = bookDataModel.getEmployeeById(id);

            if (employeeDetails == null) {
                respond404(exchange);
                return;
            }

            Map<String, Object> data = createDataModel();
            data.put("employeeDetails", employeeDetails);

            renderTemplate(exchange, "employee-details.ftlh", data);
        } catch (Exception e) {
            e.printStackTrace();
            respond404(exchange);
        }
    }
}