package se.kth.dd2480.grp25.ci;

import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;

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
      // Should perhaps write to logfile
      System.out.println("POST REQUEST");
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
    } else {
      System.out.println("INVALID REQUEST");
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
    System.out.println(req_body);
    is.close();
    exchange.close();
  }

  public void stop() {
    server.stop(0);
  }
}
