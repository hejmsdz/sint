package com.mrozwadowski.sint;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

class CookiesHandler implements HttpHandler {
    public void handle(HttpExchange exchange) throws IOException {
        OutputStream os = exchange.getResponseBody();
        String cookie = getCookieFromExchange(exchange);
        byte[] response;
        if (cookie == null) {
            exchange.getResponseHeaders().set("Set-Cookie", setCookieHeader());
            response = "i gave you a cookie".getBytes();
        } else {
            response = ("your cookie is " + cookie).getBytes();
        }
        exchange.sendResponseHeaders(200, response.length);
        os.write(response);
    }

    private String setCookieHeader() {
        String value = "d3fc89a12b"; // chosen by a fair dice roll
        return "id=" + value + "; path=/";
    }

    private String getCookieFromExchange(HttpExchange exchange) {
        return exchange.getRequestHeaders().getFirst("Cookie");
    }
}