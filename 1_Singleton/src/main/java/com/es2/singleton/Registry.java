package com.es2.singleton;
// Implementação usa (Nível 1) Lazy Initialization
// Não sendo desta forma thread-safe e não recomendada para ambientes multi-thread onde a melhor solução atual é Enum

public class Registry {

    // campo estático e privado para armazenar instância única da classe
    // inicialmente é null, sendo apenas criado quando é chamado o método getInstance()
    private static Registry instance;

    private String path;
    private String connectionString;

    // Construtor privado que impede código fora da classe de criar novo registo
    private Registry() {
        this.path = "";
        this.connectionString = "";
    }

    // Único responsável por criar(1ª chamada) ou devolver objeto Registy
    // Devolve sempre a mesma instância em chamada seguitne
    public static Registry getInstance() {
        if (instance == null) {
            instance = new Registry();
        }
        return instance;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    // Devolve connction string para a BD
    public String getConnectionString() {
        return connectionString;
    }

    // Define connction string para a BD
    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }
}
