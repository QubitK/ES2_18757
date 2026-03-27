package com.es2.loggingproject;

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
        try {
            DestinationPool pool = DestinationPool.getInstance(List.of(auth, db));

            LogDestinationInterface destAuth = pool.acquire();
            auth.outputTo(destAuth);
            pool.release(destAuth);

            LogDestinationInterface destDb = pool.acquire();
            db.outputTo(destDb);
            pool.release(destDb);

        } catch (IOException | PoolExhaustedException | ObjectNotFoundException e) {
            System.err.println("Pool error: " + e.getMessage());
        }

    }
}