package com.es2.singleton;

/**
 * Classe de demonstração do padrão Singleton aplicado ao {@link Registry}.
 *
 * <p>Mostra que, independentemente de quantas vezes se chame {@code getInstance()},
 * é sempre devolvido o <strong>mesmo objeto</strong> em memória.
 */
public class Main {

    public static void main(String[] args) {

        // --- Cliente A: configura as variáveis globais ---
        Registry registryA = Registry.getInstance();
        registryA.setPath("/var/app/files");
        registryA.setConnectionString("jdbc:postgresql://localhost:5432/mydb");

        System.out.println("=== Cliente A ===");
        System.out.println("Path             : " + registryA.getPath());
        System.out.println("Connection String: " + registryA.getConnectionString());

        // --- Cliente B: obtém a instância e lê os valores ---
        // Não usa "new Registry()" — acede sempre via getInstance()
        Registry registryB = Registry.getInstance();

        System.out.println("\n=== Cliente B ===");
        System.out.println("Path             : " + registryB.getPath());
        System.out.println("Connection String: " + registryB.getConnectionString());

        // --- Verificação da unicidade da instância ---
        // "==" compara referências em memória; se forem o mesmo objeto, é Singleton.
        System.out.println("\n=== Verificação Singleton ===");
        System.out.println("registryA == registryB? " + (registryA == registryB));
        // Esperado: true — ambos apontam para o mesmo objeto em memória
    }
}
