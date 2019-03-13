package com.mrozwadowski.sint;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

class RedirectHandler implements HttpHandler {
    public void handle(HttpExchange exchange) throws IOException {
        OutputStream os = exchange.getResponseBody();
        try {
            System.out.println(exchange.getRequestMethod() + " " + exchange.getRequestURI().toString());
            int code = getRedirectCode(exchange);
            System.out.println("redirect code: " + code);
            if (code >= 300 && code <= 399) {
                exchange.getResponseHeaders().set("Location", "/echo");
                exchange.sendResponseHeaders(code, 0);
            } else {
                byte[] response = "redirect code must be 3xx".getBytes();
                exchange.sendResponseHeaders(404, response.length);
                os.write(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            os.close();
        }
    }

    private int getRedirectCode(HttpExchange exchange) {
        try {
            String segments[] = exchange.getRequestURI().toString().split("/");
            return Integer.parseInt(segments[segments.length - 1]);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}