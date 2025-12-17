import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class MainServer {

  public static void main(String[] args) throws Exception {
    HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

    // Serve all static files
    server.createContext("/", MainServer::handleStatic);

    // API endpoint
    server.createContext("/tsp", MainServer::handleTsp);

    server.start();
    System.out.println("Server running at http://localhost:8080");
  }

  private static void handleStatic(HttpExchange exchange) throws IOException {
    String path = exchange.getRequestURI().getPath();

    // Default to index.html
    if (path.equals("/")) {
      path = "/index.html";
    }

    // Remove leading slash
    String resourcePath = "static" + path;

    // Serve file
    InputStream is = MainServer.class.getClassLoader().getResourceAsStream(resourcePath);

    if (is == null) {
      exchange.sendResponseHeaders(404, -1);
      return;
    }

    // Determine content type
    String contentType = "text/plain";
    if (path.endsWith(".html")) contentType = "text/html";
    else if (path.endsWith(".css")) contentType = "text/css";
    else if (path.endsWith(".js")) contentType = "application/javascript";
    else if (path.endsWith(".png")) contentType = "image/png";

    byte[] bytes = is.readAllBytes();
    exchange.getResponseHeaders().set("Content-Type", contentType);
    exchange.sendResponseHeaders(200, bytes.length);
    exchange.getResponseBody().write(bytes);
    exchange.close();
  }

  private static void handleTsp(HttpExchange exchange) throws IOException {
    if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
      exchange.sendResponseHeaders(405, -1);
      return;
    }

    String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);

    // body will look like: Albany,Boston,New York
    String[] points = body.split(",");

    String response;
    try {
      String[] result = TSPService.solve(points);
      response = String.join(" → ", result);
    } catch (Exception e) {
      e.printStackTrace(); // log server-side
      response = "Error: " + e.getMessage();
    }

    byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
    exchange.sendResponseHeaders(200, bytes.length);
    exchange.getResponseBody().write(bytes);
    exchange.close();
  }
}
