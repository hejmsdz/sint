package com.mrozwadowski.sint;

import com.cedarsoftware.util.io.JsonWriter;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

class EchoHandler implements HttpHandler {
    public void handle(HttpExchange exchange) throws IOException {
        byte[] response = headersToJson(exchange.getRequestHeaders());
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, response.length);
        OutputStream os = exchange.getResponseBody();
        os.write(response);
        os.close();
    }

    private byte[] headersToJson(Headers headers) {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(JsonWriter.PRETTY_PRINT, true);
        return JsonWriter.objectToJson(headers, options).getBytes();
    }
}