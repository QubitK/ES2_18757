package com.es2.singleton;

/**
 * Registo global de configurações da aplicação implementado com o padrão Singleton.
 *
 * <p>Esta classe garante que existe <strong>uma única instância</strong> ao longo
 * de toda a aplicação (lazy instantiation), centralizando o acesso a:
 * <ul>
 *   <li>O <em>path</em> da pasta onde os ficheiros são guardados</li>
 *   <li>A <em>connection string</em> da base de dados</li>
 * </ul>
 *
 * <h2>Anatomia do padrão</h2>
 * <pre>
 *  ┌────────────────────────────────────────┐
 *  │              Registry                  │
 *  ├────────────────────────────────────────┤
 *  │ - instance : Registry  (campo estático)│  ← armazena a única instância
 *  │ - path : String                        │
 *  │ - connectionString : String            │
 *  ├────────────────────────────────────────┤
 *  │ - Registry()            (privado)      │  ← ninguém usa "new" fora daqui
 *  │ + getInstance() : Registry (estático)  │  ← único ponto de acesso
 *  │ + getPath() / setPath()                │
 *  │ + getConnectionString() / setConnectionString() │
 *  └────────────────────────────────────────┘
 * </pre>
 *
 * <h2>Nota sobre thread-safety</h2>
 * Esta implementação usa <em>Lazy Initialization</em> simples (Nível 1).
 * Não é thread-safe por si só; em ambientes multi-thread recomenda-se
 * {@code synchronized} no método {@code getInstance()} ou Double-Checked Locking.
 *
 * @author ES2
 * @version 1.0
 */
public class Registry {

    // -------------------------------------------------------------------------
    // Campo estático privado — armazena a única instância da classe.
    // Começa a null; só é criado quando getInstance() for chamado pela 1.ª vez.
    // -------------------------------------------------------------------------
    private static Registry instance;

    // Campos de configuração da aplicação
    private String path;
    private String connectionString;

    // -------------------------------------------------------------------------
    // Construtor PRIVADO — impede que código externo use "new Registry()".
    // Desta forma nenhum objeto adquire a posse do Singleton.
    // -------------------------------------------------------------------------
    private Registry() {
        // Valores por omissão (podem ser alterados via setters)
        this.path = "";
        this.connectionString = "";
    }

    // -------------------------------------------------------------------------
    // getInstance() — o "porteiro" público e único ponto de entrada.
    // Lazy Initialization: a instância só é criada quando for pedida pela 1.ª vez.
    // -------------------------------------------------------------------------

    /**
     * Retorna o objeto Singleton.
     *
     * <p>Se a instância ainda não existir, é criada neste momento
     * (<em>lazy instantiation</em>). Nas chamadas seguintes é sempre
     * devolvida a mesma instância já existente.
     *
     * @return a instância única de {@code Registry}
     */
    public static Registry getInstance() {
        if (instance == null) {
            // Primeira chamada: cria a instância e guarda-a no campo estático
            instance = new Registry();
        }
        // Todas as chamadas seguintes devolvem a instância já criada
        return instance;
    }

    // -------------------------------------------------------------------------
    // Getters e Setters
    // -------------------------------------------------------------------------

    /**
     * Retorna o path onde a aplicação guarda ficheiros.
     *
     * @return o path configurado
     */
    public String getPath() {
        return path;
    }

    /**
     * Define o path onde a aplicação guarda ficheiros.
     *
     * @param path o caminho para a pasta de ficheiros
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Retorna a connection string para a base de dados.
     *
     * @return a connection string configurada
     */
    public String getConnectionString() {
        return connectionString;
    }

    /**
     * Define a connection string para a base de dados.
     *
     * @param connectionString a string de ligação à base de dados
     */
    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }
}
