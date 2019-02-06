package se.kth.dd2480.grp25.ci;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;

/**
 * A simple HttpServer that at the moment respond with "Hello World!" to localhost on port 8000, no
 * matter what path is entered after http://localhost:8000/
 */
public class CiServer {
  private HttpServer server;
  private final int port = 8000;

  public static void main(String[] args) throws IOException {
    new CiServer();
  }

  public CiServer() throws IOException {
    server = HttpServer.create(new InetSocketAddress(port), 0);
    server.createContext("/").setHandler(CiServer::handleHttpRequest);
    server.createContext("/hooks/github").setHandler(CiServer::handleWebhook);
    server.start();
    System.out.println("Server running...");
  }

  private static void handleHttpRequest(HttpExchange exchange) throws IOException {
    String response = "Hello World!";
    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.getBytes().length);
    OutputStream os = exchange.getResponseBody();
    os.write(response.getBytes());
    os.close();
  }

  private static void handleWebhook(HttpExchange exchange) throws IOException {
    if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
    } else {
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
      exchange.close();
      return;
    }

    int i;
    String req_body = "";
    InputStream is = exchange.getRequestBody();
    // Read all content of input stream
    while ((i = is.read()) != -1) {
      req_body += (char) i;
    }

    is.close();
    exchange.close();

    // Parse payload form POST request
    String commitID = parseJsonString(req_body, "after");

    if (commitID == "INVALID") {
      // Should perhaps be logged
      System.out.println("Invalid JSON file provided");
      return;
    }
    System.out.println(commitID);
  }

  public static String parseJsonString(String json, String arg) {
    String res = "";
    JsonParser parser;
    parser = new JsonParser();
    try {
      JsonElement jsonElem = parser.parse(json);
      res = jsonElem.getAsJsonObject().get(arg).getAsString();
    } catch (JsonSyntaxException e) {
      // Should perhaps be written to logfile
      res = "INVALID";
    }

    // Remove double quotes from parsing
    res = res.replaceAll("\"", "");
    return res;
  }

  public void stop() {
    server.stop(0);
  }
}
