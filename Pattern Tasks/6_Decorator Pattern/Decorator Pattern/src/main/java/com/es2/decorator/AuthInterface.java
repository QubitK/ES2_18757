package com.es2.decorator;
import java.io.IOException;

// Defines the common interface implemented by the Auth classes and all decorators classes

public interface AuthInterface {

    // AuthException - thrown when authentication fails
    public void auth(String username, String password) throws AuthException, IOException;
}
