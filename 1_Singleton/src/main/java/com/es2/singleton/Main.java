package com.es2.singleton;

public class Main {

    public static void main(String[] args) {

        // Cliente A configura as variáveis globais
        Registry registryA = Registry.getInstance();
        registryA.setPath("/var/app/files");
        registryA.setConnectionString("jdbc:postgresql://localhost:5432/mydb");

        System.out.println("=== Cliente A ===");
        System.out.println("Path             : " + registryA.getPath());
        System.out.println("Connection String: " + registryA.getConnectionString());

        // Cliente B obtem a instância e lê os valores
        // não consegue criar novo Registo, acede sempre ao mesmo via getInstance()
        Registry registryB = Registry.getInstance();

        System.out.println("\n=== Cliente B ===");
        System.out.println("Path             : " + registryB.getPath());
        System.out.println("Connection String: " + registryB.getConnectionString());

        // Verificação do SINGLETON
        // operador "==" permite comparar referências em memória, assim espera-se TRUE correspondedo ambas ao mesmo objeto
        System.out.println("\n Teste Singleton");
        System.out.println("Objeto registryA é a mesma instância de Registo que Objeto registryB? " + (registryA == registryB));
    }
}
