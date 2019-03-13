package com.mrozwadowski.sint;

import com.cedarsoftware.util.io.JsonWriter;
import com.sun.net.httpserver.*;
import org.w3c.dom.html.HTMLAnchorElement;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        int port = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/echo", new EchoHandler());
        server.createContext("/redirect", new RedirectHandler());
        server.createContext("/cookies", new CookiesHandler());
        server.createContext("/auth", new AuthHandler());
        server.createContext("/auth2", new Auth2Handler()).setAuthenticator(new MyAuthenticator());
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
}

