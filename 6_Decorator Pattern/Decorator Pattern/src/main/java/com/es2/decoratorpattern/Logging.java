package com.es2.decoratorpattern;

// Logs the activity in the console. The format is (timestamp),auth().

import java.io.IOException;

public class Logging extends Decorator {

    public Logging(AuthInterface auth){
        super(auth);
    }

    public void auth(String username, String password) throws AuthException, IOException {
        System.out.println("[LOGGING] " + new java.util.Date() + ",auth()");
        wrapped.auth(username, password); // delega para o próximo
    }
}
