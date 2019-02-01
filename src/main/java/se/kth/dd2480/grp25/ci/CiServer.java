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

  public void stop() {
    server.stop(0);
  }
}
