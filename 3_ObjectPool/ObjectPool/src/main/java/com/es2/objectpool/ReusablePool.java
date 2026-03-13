package com.es2.objectpool;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;

/* available.add((HttpURLConnection) url.openConnection());
    1. url.openConnection() devolve um objeto do tipo URLConnection (tipo genérico do Java para qualquer tipo de ligação — HTTP, FTP, etc.).
    2. efetuamos cast do objeto tipo URLConnection para tipo HttpURLConnection através de (HttpURLConnection)
    3. conexão nova adicionada à lista available
    - nesta fase, o Java cria o objeto mas não abre ainda o socket TCP — a ligação real ao servidor só acontece quando chamares métodos como connect() ou getInputStream()
 */


// Implementação do padrão Object Pool para conexões HTTP ao site do IPV
// É um Singleton: existe apenas uma instância do pool em toda a aplicação
public class ReusablePool {

    private static int MAX_POOL_SIZE = 10; // max de conexões permitidas
    private static final String IPV_URL = "https://www.ipv.pt";

    private static ReusablePool instance; // Instância unica do pool (singleton)

    private LinkedList<HttpURLConnection> available; // conexoes disponiveis para serem adquiridas(acquire())
    private LinkedList<HttpURLConnection> inUse; // conexoes em uso, já adquiridas e que podem sere devolvidas(release())

    // construtor privado: impede criação direta de instâncias fora desta classe
    private ReusablePool() throws IOException {
        available = new LinkedList<>(); // Inicializa a lista de conexões disponíveis
        inUse = new LinkedList<>(); // Inicializa a lista de conexões em uso (começa vazia)

        URL url = new URL(IPV_URL);  // Cria o objeto URL com o endereço do IPV

        // Cria numero maximo de conexões HTTP e adicionar à lista de disponíveis
        for (int i = 0; i < MAX_POOL_SIZE; i++) {
            available.add((HttpURLConnection) url.openConnection()); // explicado acima
        }

        // Garante que todas as conexões são fechadas quando a JVM termina
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            for (HttpURLConnection conn : available) conn.disconnect();
            for (HttpURLConnection conn : inUse) conn.disconnect();
            available.clear();
            inUse.clear();
            System.out.println("\n\nConections fully released on closing...");
        }));
    }

    // Metodo estático e sincronizado que devolve a instância única do pool, usa synchronazed para adicionar thread-safe
    // garantindo que apenas uma thread executa este metodo de cada vez
    public static synchronized ReusablePool getInstance() throws IOException {
        if (instance == null) {
            instance = new ReusablePool(); // Se ainda não existe instância, cria-a com o tamanho máximo por defeito
        }
        return instance;
    }

    // Adquire uma conexão disponível do pool ('synchronized' garante que duas threads não adquirem a mesma conexão simultaneamente)
    public synchronized HttpURLConnection acquire() throws PoolExhaustedException {
        if (available.isEmpty()) { // Verifica se há conexões disponíveis; se não houver, lança exceção
            throw new PoolExhaustedException("Pool esgotado: sem conexões disponíveis.");
        }

        HttpURLConnection conn = available.removeFirst(); // Remove a primeira conexão da lista de disponíveis

        inUse.add(conn); // Adiciona-a à lista de conexões em uso

        return conn; // Devolve a conexão ao chamador
    }

    // Devolve uma conexão ao pool depois de ser usada ('synchronized' garante que a devolução é atómica (thread-safe))
    public synchronized void release(HttpURLConnection conn) throws ObjectNotFoundException {
        if (!inUse.remove(conn)) { // Tenta remover a conexão da lista de em uso; remove() devolve false se não existir
            throw new ObjectNotFoundException("Conexão não pertence a este pool."); // A conexão não pertence a este pool: lança exceção
        }

        available.add(conn); // devolve a conexão à lista de disponíveis para poder ser reutilizada
    }

    public synchronized void setMaxPoolSize(int size) throws IOException {
        MAX_POOL_SIZE = size; // define novo numero limite de pool
        resetPool(); // reinicia o pool com o novo tamanho
    }

    // limpa tudo e recria conexões
    public synchronized void resetPool() throws IOException {
        for (HttpURLConnection conn : available) conn.disconnect();
        for (HttpURLConnection conn : inUse) conn.disconnect();
        available.clear();
        inUse.clear();
        URL url = new URL(IPV_URL);
        for(int i = 0 ; i < MAX_POOL_SIZE ; i++) {
            available.add((HttpURLConnection) url.openConnection());
        }
    }
}
