package com.mrozwadowski.sint;

import com.sun.net.httpserver.BasicAuthenticator;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class Auth2Handler implements HttpHandler {
    public void handle(HttpExchange exchange) throws IOException {
        OutputStream os = exchange.getResponseBody();
        byte[] response;
        response = "welcome to another super secret page".getBytes();
        exchange.sendResponseHeaders(200, response.length);
        os.write(response);
    }
}
