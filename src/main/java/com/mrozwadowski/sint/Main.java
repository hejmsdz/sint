package com.mrozwadowski.sint;

import com.cedarsoftware.util.io.JsonWriter;
import com.sun.net.httpserver.*;
import org.w3c.dom.html.HTMLAnchorElement;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws Exception {
        int port = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/echo", new EchoHandler());
        server.createContext("/", new RootHandler());
        System.out.println("Starting server on port: " + port);
        server.start();
    }

    static class RootHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            byte[] response = loadFile("index.html");
            exchange.getResponseHeaders().set("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, response.length);
            OutputStream os = exchange.getResponseBody();
            os.write(response);
            os.close();
        }

        private byte[] loadFile(String path) throws IOException {
            // InputStreamReader reader = new InputStreamReader(new FileInputStream("index.html"));
            return Files.readAllBytes(Paths.get(path));
        }
    }

    static class EchoHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            byte[] response = headersToJson(exchange.getRequestHeaders());
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.length);
            OutputStream os = exchange.getResponseBody();
            os.write(response);
            os.close();
        }

        private byte[] headersToJson(Headers headers) {
            return JsonWriter.objectToJson(headers).getBytes();
        }
    }

}

