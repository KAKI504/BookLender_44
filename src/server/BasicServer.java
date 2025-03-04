package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public abstract class BasicServer {

    private final HttpServer server;
    private final String dataDir = "data";
    private Map<String, RouteHandler> routes = new HashMap<>();

    protected BasicServer(String host, int port) throws IOException {
        server = createServer(host, port);
        registerCommonHandlers();
    }

    protected final void registerPost(String route, RouteHandler handler) {
        getRoutes().put("POST " + route, handler);
    }

    private static String makeKey(String method, String route) {
        return String.format("%s %s", method.toUpperCase(), route);
    }

    private static String makeKey(HttpExchange exchange) {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();

        int index = path.lastIndexOf(".");
        String extOrPath = index != -1 ? path.substring(index).toLowerCase() : path;

        return makeKey(method, extOrPath);
    }

    private static void setContentType(HttpExchange exchange, ContentType type) {
        exchange.getResponseHeaders().set("Content-Type", String.valueOf(type));
    }

    private static HttpServer createServer(String host, int port) throws IOException {
        String msg = "Starting server on http://%s:%s/%n";
        System.out.printf(msg, host, port);
        InetSocketAddress address = new InetSocketAddress(host, port);
        return HttpServer.create(address, 50);
    }

    private void registerCommonHandlers() {
        server.createContext("/", this::handleIncomingServerRequests);
        registerGet("/", exchange -> sendFile(exchange, makeFilePath("index.html"), ContentType.TEXT_HTML));
        registerFileHandler(".css", ContentType.TEXT_CSS);
        registerFileHandler(".html", ContentType.TEXT_HTML);
        registerFileHandler(".jpg", ContentType.IMAGE_JPEG);
        registerFileHandler(".jpeg", ContentType.IMAGE_JPEG);
        registerFileHandler(".png", ContentType.IMAGE_PNG);
    }

    protected final void registerGet(String route, RouteHandler handler) {
        getRoutes().put("GET " + route, handler);
    }

    protected final void registerFileHandler(String fileExt, ContentType type) {
        registerGet(fileExt, exchange -> sendFile(exchange, makeFilePath(exchange), type));
    }

    protected final Map<String, RouteHandler> getRoutes() {
        return routes;
    }

    protected final void sendFile(
            HttpExchange exchange,
            Path pathToFile,
            ContentType contentType
    ) {
        try {
            if (Files.notExists(pathToFile)) {
                respond404(exchange);
                return;
            }
            byte[] data = Files.readAllBytes(pathToFile);
            sendByteData(exchange, ResponseCodes.OK, contentType, data);
        } catch (IOException e) {
        }
    }

    private Path makeFilePath(HttpExchange exchange) {
        return makeFilePath(exchange.getRequestURI().getPath());
    }

    protected Path makeFilePath(String... s) {
        return Path.of(dataDir, s);
    }

    protected final void sendByteData(
            HttpExchange exchange,
            ResponseCodes responseCode,
            ContentType contentType,
            byte[] data
    ) throws IOException {
        try (OutputStream output = exchange.getResponseBody()) {
            setContentType(exchange, contentType);
            exchange.sendResponseHeaders(responseCode.getCode(), 0);
            output.write(data);
            output.flush();
        }
    }

    protected void respond404(HttpExchange exchange) {
        try {
            byte[] data = "404 Not found".getBytes();
            sendByteData(exchange, ResponseCodes.NOT_FOUND, ContentType.TEXT_PLAIN, data);
        } catch (IOException e) {
        }
    }

    private void handleIncomingServerRequests(HttpExchange exchange) {
        String requestKey = makeKey(exchange);
        RouteHandler route = getRoutes().get(requestKey);

        if (route == null) {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();

            for (Map.Entry<String, RouteHandler> entry : getRoutes().entrySet()) {
                String key = entry.getKey();

                if (key.startsWith(method + " ")) {
                    String pattern = key.substring((method + " ").length());

                    if (pattern.contains("\\d+")) {
                        String regex = "^" + pattern.replace("\\d+", "(\\d+)") + "$";

                        if (path.matches(regex)) {
                            route = entry.getValue();
                            break;
                        }
                    }
                }
            }
        }

        if (route != null) {
            route.handle(exchange);
        } else {
            respond404(exchange);
        }
    }

    public final void start() {
        server.start();
    }

    protected void registerStaticResourcesHandler() {
        server.createContext("/data/images/", exchange -> {
            try {
                String requestPath = exchange.getRequestURI().getPath();
                String filePath = requestPath.substring("/data/".length());

                ContentType contentType = ContentType.TEXT_PLAIN;
                if (filePath.endsWith(".jpg") || filePath.endsWith(".jpeg")) {
                    contentType = ContentType.IMAGE_JPEG;
                } else if (filePath.endsWith(".png")) {
                    contentType = ContentType.IMAGE_PNG;
                } else if (filePath.endsWith(".css")) {
                    contentType = ContentType.TEXT_CSS;
                }

                sendFile(exchange, makeFilePath(filePath), contentType);
            } catch (Exception e) {
                respond404(exchange);
            }
        });
    }
}