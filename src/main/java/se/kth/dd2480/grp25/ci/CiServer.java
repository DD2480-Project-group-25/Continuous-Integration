package se.kth.dd2480.grp25.ci;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
  private final EventQueue eventQueue;

  public static void main(String[] args) throws IOException {
    new CiServer();
  }

  public CiServer() throws IOException {
    eventQueue = new EventQueue();
    server = HttpServer.create(new InetSocketAddress(port), 0);
    server.createContext("/").setHandler(CiServer::handleHttpRequest);
    server.createContext("/hooks/github").setHandler(this::handleWebhook);
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

  private void handleWebhook(HttpExchange exchange) {
    String req_body = "";
    try {
      if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
      } else {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
        exchange.close();
        return;
      }

      int i;
      InputStream is = exchange.getRequestBody();
      // Read all content of input stream
      while ((i = is.read()) != -1) {
        req_body += (char) i;
      }

      is.close();
      exchange.close();
    } catch (Exception e) {
      System.err.println(e);
    }

    // Parse payload from POST request, "after" is the commit hash
    String[] arg = new String[] {"after"};
    String commitID = parseJsonString(req_body, arg);
    String[] repo = new String[] {"repository", "full_name"};
    String repoName = parseJsonString(req_body, repo);

    if (commitID == "" || repoName == "") {
      // Should perhaps be logged
      System.err.println("Invalid JSON file provided");
      return;
    }

    Event webhookEvent =
        new Event(commitID, Event.Type.WEB_HOOK, Event.Status.SUCCESSFUL, "", repoName);
    try {
      eventQueue.insert(webhookEvent);
    } catch (InterruptedException e) {
      System.err.println(e);
    }
  }

  public static String parseJsonString(String json, String[] arg) {
    String res = "";
    try {
      JsonParser parser = new JsonParser();
      JsonElement jsonTree = parser.parse(json);

      if (jsonTree.isJsonObject()) {
        JsonObject jsonObject = jsonTree.getAsJsonObject();
        for (int i = 0; i < arg.length; i++) {
          if (i == arg.length - 1) {
            JsonElement jsonElement = jsonObject.get(arg[i]);
            res = jsonElement.getAsString();
          } else {
            jsonObject = jsonObject.get(arg[i]).getAsJsonObject();
          }
        }
      }
    } catch (Exception e) {
      System.err.println(e);
    }

    // Remove double quotes from parsing
    res = res.replaceAll("\"", "");
    return res;
  }

  public void stop() {
    server.stop(0);
  }
}
