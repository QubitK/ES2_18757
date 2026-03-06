package com.es2.objectpool;

import java.io.IOException;
import java.net.HttpURLConnection;

public class Main {

    static void main() {

        // --- 1. Implementação casual de Object Pool ---
        System.out.println("--- 1: Implementação normal ---");
        try {
            // Cria 10 conexões ao IPV:
            ReusablePool pool = ReusablePool.getInstance(); // Obtém a instância única do pool (Singleton)

            HttpURLConnection conn1 = pool.acquire(); // Adquire a primeira conexão disponível do pool
            System.out.println("Conexão 1 adquirida: " + conn1);

            HttpURLConnection conn2 = pool.acquire(); // Adquire uma segunda conexão disponível do pool
            System.out.println("Conexão 2 adquirida: " + conn2);

            pool.release(conn1); // Devolve a primeira conexão ao pool para ser reutilizada
            System.out.println("Conexão 1 devolvida ao pool.");

            pool.release(conn2);
            System.out.println("Conexão 2 devolvida ao pool.");

        } catch (IOException e) {
            System.out.println("Erro de IO: " + e.getMessage()); // Erro ao criar o pool ou as conexões HTTP
        } catch (PoolExhaustedException e) {
            System.out.println("Pool esgotado: " + e.getMessage()); // Pool sem conexões disponíveis
        } catch (ObjectNotFoundException e) {
            System.out.println("Objeto não encontrado: " + e.getMessage()); // conexão não pertence ao pool
        }

        // ---  2. Esgotar o pool (PoolExhaustedException) ---
        System.out.println("\n--- 2: Teste para Esgotar o pool ---");
        try {
            ReusablePool pool = ReusablePool.getInstance();  // Obtém a mesma instância única do pool (Singleton)

            HttpURLConnection[] conexoes = new HttpURLConnection[10]; // Obter as 10 conexões que queremos dispobilizar de modo a esgotar o pool
            for (int i = 0 ; i < 10 ; i++) {
                conexoes[i] = pool.acquire(); // Cada acquire() remove uma conexão de 'available' e adiciona-a a 'inUse'
                System.out.println("Conexão " + (i + 1) + " adquirida.");
            }
            pool.acquire(); // tenta adquirir uma 11ª conexão —> o pool está esgotado, lança exceção
        } catch (IOException e) {
            System.out.println("Erro de IO: " + e.getMessage());
        } catch (PoolExhaustedException e) {
            System.out.println("Exceção esperada : " + e.getMessage()); // Exceção esperada: pool sem conexões disponíveis
        }

        // --- 3. Devolver conexão que não pertence ao pool (ObjectNotFoundException) ---
        System.out.println("\n--- 3. Devolver conexão inválida ---");
        try {
            ReusablePool pool = ReusablePool.getInstance();

            // Cria uma conexão HTTP externamente (fora do pool)
            HttpURLConnection connExterna = (HttpURLConnection) new java.net.URL("https://www.ipv.pt").openConnection();

            pool.release(connExterna); // Devolver ao pool uma conexão que não foi adquirida dele: lança exceção

        } catch (IOException e) {
            System.out.println("Erro de IO: " + e.getMessage());
        } catch (ObjectNotFoundException e) {
            // Apenas release() é chamado neste bloco, logo só esta exceção é possível
            System.out.println("Exceção esperada : " + e.getMessage());
        }
    }
}

/*
  Main.java — cenários de demonstração:
  ┌──────────────────────┬───────────────────────────────────────────────────────────────┐
  │       Cenário        │                        O que demonstra                        │
  ├──────────────────────┼───────────────────────────────────────────────────────────────┤
  │ 1 — Uso normal       │ acquire() + release() sem erros                               │
  ├──────────────────────┼───────────────────────────────────────────────────────────────┤
  │ 2 — Pool esgotado    │ Adquire 10, tenta a 11ª → PoolExhaustedException              │
  ├──────────────────────┼───────────────────────────────────────────────────────────────┤
  │ 3 — Conexão inválida │ Devolve conexão criada fora do pool → ObjectNotFoundException │
  └──────────────────────┴───────────────────────────────────────────────────────────────┘


Java HttpURLConnection:
    - Objeto que representa uma conexão HTTP. O Java abre um socket TCP para o servidor remoto (ex.: site do IPV).
    - Criar este objeto é caro em termos de tempo e recursos, pois envolve:
          - resolução DNS
          - handshake TCP
          - (possivelmente) handshake TLS/HTTPS

Lógica:
    - Singleton (getInstance com synchronized) — garante uma só instância do pool
    - available / inUse — duas listas para saber quais conexões estão livres e quais estão a ser usadas
    - acquire() — tira uma conexão da lista available e passa-a para inUse; lança PoolExhaustedException se não houver nenhuma livre
    - release() — devolve uma conexão de inUse para available; lança ObjectNotFoundException se a conexão não pertencer ao pool
    - synchronized nos métodos — garante thread-safety
 */