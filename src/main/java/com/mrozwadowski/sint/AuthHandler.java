package com.mrozwadowski.sint;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.io.IOException;
import java.io.OutputStream;

class AuthHandler implements HttpHandler {
    public void handle(HttpExchange exchange) throws IOException {
        if (areCredentialsPresent(exchange)) {
            if (areCredentialsCorrect(exchange)) {
                System.out.println("good boy");
                sendResponse(exchange, 200, "welcome to my super secret page");
            } else {
                System.out.println("bad boy");
                sendResponse(exchange, 403, "you are not allowed here");
            }
        } else {
            System.out.println("let's have him authenticate");
            exchange.getResponseHeaders().set("WWW-Authenticate", "Basic");
            sendResponse(exchange,401,"");
        }
    }

    private void sendResponse(HttpExchange exchange, int code, String text) throws IOException {
        byte[] response = text.getBytes();
        OutputStream os = exchange.getResponseBody();
        exchange.sendResponseHeaders(code, response.length);
        os.write(response);
        os.close();
    }

    private boolean areCredentialsPresent(HttpExchange exchange) {
        String authorization = authHeader(exchange);
        return authorization != null && authorization.startsWith("Basic ");
    }

    private boolean areCredentialsCorrect(HttpExchange exchange) {
        String encodedCredentials = authHeader(exchange).split(" ")[1];
        String credentials = new String(Base64.decode(encodedCredentials));
        System.out.println("credentials: " + credentials);
        return credentials.equals("nobody:whatever");
    }

    private String authHeader(HttpExchange exchange) {
        return exchange.getRequestHeaders().getFirst("Authorization");
    }
}