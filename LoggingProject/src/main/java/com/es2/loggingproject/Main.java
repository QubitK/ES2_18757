package com.es2.loggingproject;

import com.es2.loggingproject.M1_config.LogConfig;
import com.es2.loggingproject.M1_config.LogLevel;
import com.es2.loggingproject.M2_factory.*;
import com.es2.loggingproject.M3_bridge_destination.ConsoleDestination;
import com.es2.loggingproject.M3_bridge_destination.FileDestination;
import com.es2.loggingproject.M3_bridge_destination.LogDestinationInterface;
import com.es2.loggingproject.M3_bridge_destination.Logger;
import com.es2.loggingproject.M4_composite_category.LogCategory;
import com.es2.loggingproject.M4_composite_category.LogComponent;
import com.es2.loggingproject.M4_composite_category.LogEntry;
import com.es2.loggingproject.M5_object_pool_destination.DestinationPool;
import com.es2.loggingproject.M5_object_pool_destination.ObjectNotFoundException;
import com.es2.loggingproject.M5_object_pool_destination.PoolExhaustedException;
import com.es2.loggingproject.M6_memento_system_backup.LogSystemCaretaker;
import com.es2.loggingproject.M6_memento_system_backup.LogSystemOriginator;
import com.es2.loggingproject.M7_decorator_monitoring.CategoryDecorator;
import com.es2.loggingproject.M7_decorator_monitoring.MonitoringDecorator;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("---------------------------- ES2: LOGGING PROJECT ----------------------------\n");
        // M1: SINGLETON
        LogConfig.INSTANCE.setMinimumLevel(LogLevel.INFO);


        // M2: FACTORY METHOD
        LogCreator Debug_Creator = new DebugLogCreator();
        LogCreator Error_Creator = new ErrorLogCreator();
        LogCreator Info_Creator = new InfoLogCreator();
        LogCreator Warning_Creator = new WarningLogCreator();


        // M3: BRIDGE PATTERN
        System.out.println("\n\n==========================  M3: BRIDGE PATTERN ==========================");

        Logger logger = new Logger();
        ConsoleDestination Consola = new ConsoleDestination();
        FileDestination File = new FileDestination("logs.txt");
        String consoleID = logger.addDestination(Consola);
        String fileID = logger.addDestination(File);

        System.out.println("------------ Uso direto de Logger ------------");
        logger.logAllDestinations(Info_Creator.createLog("Sistema iniciado."));
        logger.logDestination(consoleID, Warning_Creator.createLog("Aviso para a console."));
        logger.logDestination(fileID, Error_Creator.createLog("Erro para o ficheiro."));


        // M4: COMPOSITE PATTERN
        System.out.println("\n\n========================= M4: COMPOSITE PATTERN =========================");

        LogCategory auth = new LogCategory("Autenticação");
        auth.add(new LogEntry(Info_Creator.createLog("Autenticação efetuada com sucesso.")));
        auth.add(new LogEntry(Warning_Creator.createLog("Autenticação falhou.")));

        LogCategory db = new LogCategory("Base de Dados");
        db.add(new LogEntry(Debug_Creator.createLog("Consulta executada com sucesso."))); // É FILTRADO EM LogEntry.OutputTo
        db.add(new LogEntry(Error_Creator.createLog("Erro no registo de dados.")));

        LogCategory root = new LogCategory("Sistema");
        root.add(auth);
        root.add(db);

        System.out.println("\n ------------ [Categoria: Auth] ---> Consola ------------");
        auth.outputTo(Consola);

        System.out.println("\n ------------ [Categoria: Base de Dados] ---> Ficheiro ------------");
        db.outputTo(File);

        System.out.println("\n ------------  [Categorias: Árvore Completa] ---> Consola ------------");
        root.outputTo(Consola);


        // M5: OBJECT POOL - Pool of Destination objects, criados por categorias existentes
        System.out.println("\n\n============================ M5: OBJECT POOL ============================");
        DestinationPool pool = null;
        try {
            pool = DestinationPool.getInstance(List.of(auth, db));

            LogDestinationInterface destAuth = pool.acquire();
            auth.outputTo(destAuth);
            pool.release(destAuth);

            LogDestinationInterface destDb = pool.acquire();
            db.outputTo(destDb);
            pool.release(destDb);

        } catch (IOException | PoolExhaustedException | ObjectNotFoundException e) {
            System.err.println("Pool error: " + e.getMessage());
        }

        // M6: MEMENTO PATTERN
        System.out.println("\n\n============================== M6: MEMENTO ==============================");

        LogSystemCaretaker caretaker = new LogSystemCaretaker(new LogSystemOriginator(logger, List.of(auth, db)));

        // Estado inicial: nível INFO, destinations logs.txt + consola
        System.out.println("\n--- Estado inicial ---");
        System.out.println("Nível mínimo: " + LogConfig.INSTANCE.getMinimumLevel());
        System.out.println("Destinations: " + logger.getDestinations().keySet());

        // Snapshot 1 — guarda estado inicial
        caretaker.takeSnapshot();

        // Alteração de estado
        System.out.println("\n--- Alteração de estado ---");
        LogConfig.INSTANCE.setMinimumLevel(LogLevel.ERROR);
        logger.addDestination(new FileDestination("extra.txt"));
        System.out.println("Nível mínimo: " + LogConfig.INSTANCE.getMinimumLevel());
        System.out.println("Destinations: " + logger.getDestinations().keySet());

        // Snapshot 2 — guarda estado alterado
        caretaker.takeSnapshot();

        // Nova alteração
        System.out.println("\n--- Nova alteração de estado ---");
        LogConfig.INSTANCE.setMinimumLevel(LogLevel.DEBUG);
        logger.clearDestinations();
        System.out.println("Nível mínimo: " + LogConfig.INSTANCE.getMinimumLevel());
        System.out.println("Destinations: " + logger.getDestinations().keySet());

        // Restore para snapshot 2
        System.out.println("\n--- Restore para snapshot 2 ---");
        caretaker.restoreSnapshot(1);
        System.out.println("Nível mínimo: " + LogConfig.INSTANCE.getMinimumLevel());
        System.out.println("Destinations: " + logger.getDestinations().keySet());

        // Restore para snapshot 1
        System.out.println("\n--- Restore para snapshot 1 ---");
        caretaker.restoreSnapshot(0);
        System.out.println("Nível mínimo: " + LogConfig.INSTANCE.getMinimumLevel());
        System.out.println("Destinations: " + logger.getDestinations().keySet());

        // Verificação: log com nível restaurado (INFO) — deve aparecer
        System.out.println("\n--- Verificação após restore ---");
        logger.logAllDestinations(Info_Creator.createLog("Log após restore do Memento."));
        logger.logAllDestinations(Debug_Creator.createLog("Este DEBUG não deve aparecer (nível mínimo: INFO)."));

        // M7: DECORATOR PATTERN
        System.out.println("\n\n============================= M7: DECORATOR =============================");

        // === Exemplo 1: Decorar entradas individuais ===
        System.out.println("\n--- Exemplo com Monitoring + Category Decorator ---");

        LogRecordInterface loginRecord = Info_Creator.createLog("Utilizador admin efetuou login.");
        LogComponent loginEntry = new LogEntry(loginRecord);

        // Cadeia: MonitoringDecorator → CategoryDecorator → LogEntry
        LogComponent monitoredLogin = new MonitoringDecorator(
                new CategoryDecorator(loginEntry, "AUTH")
        );

        LogCategory authDecorated = new LogCategory("Autenticação Decorada");
        authDecorated.add(monitoredLogin);

        System.out.println("Executando output com decorators...");
        authDecorated.outputTo(Consola);

        // Mostrar resumo do monitoring
        if (monitoredLogin instanceof MonitoringDecorator md) {
            System.out.println(md.getSummary());
        }

        // === Exemplo 2: Decorar com threshold baixo para forçar alerta ===
        System.out.println("\n--- Exemplo com threshold baixo (alerta deve disparar) ---");

        LogComponent errorEntry = new LogEntry(Error_Creator.createLog("Falha crítica no sistema."));

        LogComponent monitoredError = new MonitoringDecorator(
                new CategoryDecorator(errorEntry, "SECURITY"),
                3   // threshold muito baixo para demonstrar o alerta
        );

        LogCategory security = new LogCategory("Segurança");
        security.add(monitoredError);

        // Executar várias vezes para ultrapassar o threshold
        for (int i = 0; i < 5; i++) {
            security.outputTo(Consola);
        }

        if (monitoredError instanceof MonitoringDecorator md) {
            System.out.println(md.getSummary());
        }

        // ====================== FECHO DE RECURSOS ======================
        System.out.println("\n\nFechando recursos abertos...");
        if (pool != null) {
            try {
                pool.close();   // método que deve existir no teu DestinationPool
            } catch (Exception e) {
                System.err.println("Erro ao fechar pool: " + e.getMessage());
            }
        }

        System.out.println("\n============================= FIM DO PROGRAMA =============================");
    }
}