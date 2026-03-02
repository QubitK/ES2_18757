package com.es2.objectpool;

// Exceção lançada quando se tenta adquirir uma conexão mas o pool está esgotado (sem conexões disponíveis)
public class PoolExhaustedException extends Exception {

    // Construtor que recebe uma mensagem descritiva do erro
    public PoolExhaustedException(String message) {
        // Passa a mensagem para a classe pai (Exception), tornando-a acessível via getMessage()
        super(message);
    }
}
