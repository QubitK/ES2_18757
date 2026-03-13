package com.es2.objectpool;

// Exceção lançada quando se tenta devolver ao pool uma conexão que não lhe pertence
public class ObjectNotFoundException extends Exception {

    // Construtor que recebe uma mensagem descritiva do erro
    public ObjectNotFoundException(String message) {
        // Passa a mensagem para a classe pai (Exception), tornando-a acessível via getMessage()
        super(message);
    }
}
