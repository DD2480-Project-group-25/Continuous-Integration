package se.kth.dd2480.grp25.ci;

import com.sun.net.httpserver.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class CiServer {
    private static final int port = 8000;

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/").setHandler(CiServer::handleHttpRequest);
        server.start();
        System.out.println("Server running...");
    }

    private static void handleHttpRequest(HttpExchange exchange) throws IOException {
        String response = "Hello World!";
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}