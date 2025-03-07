package lesson44;

import com.sun.net.httpserver.HttpExchange;
import server.Cookie;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Lesson46Server extends Lesson45Server {
    private final Map<String, String> sessions = new HashMap<>();
    private static final int SESSION_TIMEOUT = 600;
    private static final String SESSION_COOKIE_NAME = "sessionId";

    public Lesson46Server(String host, int port) throws IOException {
        super(host, port);

        registerGet("/cookie", this::cookieHandler);

        registerGet("/logout", this::handleLogout);
    }

    private void setCookie(HttpExchange exchange, Cookie cookie) {
        String headerValue = cookie.toString();
        exchange.getResponseHeaders().add("Set-Cookie", headerValue);
    }

    private String getCookie(HttpExchange exchange) {
        return exchange.getRequestHeaders()
                .getFirst("Cookie") != null ? exchange.getRequestHeaders().getFirst("Cookie") : "";
    }

    private void cookieHandler(HttpExchange exchange) {
        Map<String, Object> data = new HashMap<>();

        Cookie sessionCookie = Cookie.make("userId", "!@#$%ˆ&*())_+"); //%21%40%23%24%25%CB%86%26*%28%29%29_%2B
        setCookie(exchange, sessionCookie);

        Cookie c1 = Cookie.make("user%Id", "456");
        setCookie(exchange, c1);

        Cookie c2 = Cookie.make("user-mail", "qwe@qwe.qwe");
        setCookie(exchange, c2);

        Cookie c3 = Cookie.make("restricted()<>#,;:\\\"/[]?={}", "()<>#,;:\\\"/[]?={}");
        setCookie(exchange, c3);

        String cookieString = getCookie(exchange);

        Map<String, String> cookies = Cookie.parse(cookieString);

        String timesName = "times";
        String cookieVal = cookies.getOrDefault(timesName, "0");
        int timesVal = Integer.parseInt(cookieVal) + 1;
        Cookie times = Cookie.make(timesName, timesVal);
        setCookie(exchange, times);
        data.put(timesName, timesVal);

        data.put("cookies", cookies);
        renderTemplate(exchange, "cookie/cookie.ftlh", data);
    }

    @Override
    protected void handleLogin(HttpExchange exchange) {
        String raw = getBody(exchange);
        Map<String, String> formData = parseFormData(raw);

        String email = formData.get("email");
        String password = formData.get("password");

        Employee employee = bookDataModel.findEmployeeByEmail(email);
        if (employee != null && password != null && password.equals(employee.getPassword())) {
            currentEmployee = employee;

            String sessionId = createSession(employee.getEmail());

            Cookie sessionCookie = Cookie.make(SESSION_COOKIE_NAME, sessionId);
            sessionCookie.setMaxAge(SESSION_TIMEOUT);
            sessionCookie.setHttpOnly(true);

            setCookie(exchange, sessionCookie);

            redirect303(exchange, "/profile");
            return;
        }

        Map<String, Object> data = createDataModel();
        data.put("error", "Неверный email или пароль");
        renderTemplate(exchange, "login.ftlh", data);
    }

    private String createSession(String email) {
        String sessionId = UUID.randomUUID().toString();
        sessions.put(sessionId, email);
        return sessionId;
    }

    private void handleLogout(HttpExchange exchange) {
        String cookieString = getCookie(exchange);
        Map<String, String> cookies = Cookie.parse(cookieString);
        String sessionId = cookies.get(SESSION_COOKIE_NAME);

        if (sessionId != null) {
            sessions.remove(sessionId);

            Cookie sessionCookie = Cookie.make(SESSION_COOKIE_NAME, "");
            sessionCookie.setMaxAge(0);
            setCookie(exchange, sessionCookie);
        }

        currentEmployee = null;

        redirect303(exchange, "/login");
    }

    @Override
    protected void handleRoot(HttpExchange exchange) {
        if (isAuthenticated(exchange)) {
            redirect303(exchange, "/profile");
        } else {
            renderTemplate(exchange, "login.ftlh", createDataModel());
        }
    }

    @Override
    protected void handleProfile(HttpExchange exchange) {
        if (!isAuthenticated(exchange)) {
            redirect303(exchange, "/login");
            return;
        }
        renderTemplate(exchange, "employee-profile.ftlh", createDataModel());
    }

    @Override
    protected void handleBooks(HttpExchange exchange) {
        if (!isAuthenticated(exchange)) {
            redirect303(exchange, "/login");
            return;
        }
        renderTemplate(exchange, "books.ftlh", createDataModel());
    }

    @Override
    protected void handleBookDetails(HttpExchange exchange) {
        if (!isAuthenticated(exchange)) {
            redirect303(exchange, "/login");
            return;
        }

        super.handleBookDetails(exchange);
    }

    protected boolean isAuthenticated(HttpExchange exchange) {
        if (currentEmployee != null) {
            return true;
        }

        String cookieString = getCookie(exchange);
        Map<String, String> cookies = Cookie.parse(cookieString);
        String sessionId = cookies.get(SESSION_COOKIE_NAME);

        if (sessionId != null && sessions.containsKey(sessionId)) {
            String email = sessions.get(sessionId);
            currentEmployee = bookDataModel.findEmployeeByEmail(email);
            return currentEmployee != null;
        }
        return false;
    }

    @Override
    protected void handleBorrowBook(HttpExchange exchange) {
        if (!isAuthenticated(exchange)) {
            redirect303(exchange, "/login");
            return;
        }
        super.handleBorrowBook(exchange);
    }

    @Override
    protected void handleReturnBook(HttpExchange exchange) {
        if (!isAuthenticated(exchange)) {
            redirect303(exchange, "/login");
            return;
        }
        super.handleReturnBook(exchange);
    }
}