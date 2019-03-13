package com.mrozwadowski.sint;

import com.sun.net.httpserver.BasicAuthenticator;

public class MyAuthenticator extends BasicAuthenticator {
    public MyAuthenticator() {
        super("my secret realm");
    }

    @Override
    public boolean checkCredentials(String username, String password) {
        return username.equals("nobody") && password.equals("whatever");
    }
}