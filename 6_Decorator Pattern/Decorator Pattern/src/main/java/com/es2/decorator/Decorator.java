package com.es2.decorator;
import java.io.IOException;

// Represents the base class of all decorators.

public class Decorator implements AuthInterface{

    // sendo protected subclasses (Logging, CommonWordsValidator) herdam este campo automaticamente
    protected AuthInterface wrapped; // para guardar referência ao objeto envolvido

    public Decorator(AuthInterface auth){
        this.wrapped = auth;
    }

    public void auth(String username, String password) throws AuthException, IOException {
        wrapped.auth(username, password); // delega via polimorfismo por omissão passando o objeto
    }
}
