package com.es2.loggingproject;

public class Main {
    public static void main(String[] args) {

        System.out.println("---------------------------- ES2: LOGGING PROJECT ----------------------------\n");
        // M1: SINGLETON
        LogConfig.INSTANCE.setMinimumLevel(LogLevel.DEBUG);

        // M2: FACTORY METHOD
        LogCreator Debug_Creator = new DebugLogCreator();
        LogCreator Error_Creator = new ErrorLogCreator();
        LogCreator Info_Creator = new InfoLogCreator();
        LogCreator Warning_Creator = new WarningLogCreator();
//
//        LogRecordInterface Debug_Logger = Debug_Creator.createLog("DEBUG LOG TEST MESSAGE");
//        LogRecordInterface Error_Logger = Error_Creator.createLog("ERROR LOG TEST MESSAGE");
//        LogRecordInterface Info_Logger = Info_Creator.createLog("INFO LOG TEST MESSAGE");
//        LogRecordInterface Warning_Logger = Warning_Creator.createLog("WARNING LOG TEST MESSAGE");


        // M3: BRIDGE PATTERN
//        Logger logger = new Logger();
//        String Console_Destination_ID = logger.addDestination(new ConsoleDestination());
//        String file_ID = logger.addDestination(new FileDestination("logs.txt"));
//
//        System.out.println("\nLoggar para todos os destinos criados:");
//        logger.logAllDestinations(Debug_Logger);
//        logger.logAllDestinations(Error_Logger);
//
//        System.out.println("\nLoggar para destino especifico:");
//        logger.logDestination(Console_Destination_ID, Info_Logger);
//        logger.logDestination(file_ID, Warning_Logger);

        // M4: COMPOSITE PATTERN
        // M4: COMPOSITE PATTERN
        LogCategory authCategory = new LogCategory("autenticacao");
        authCategory.add(new LogEntry(Debug_Creator.createLog("Tentativa de login")));
        authCategory.add(new LogEntry(Error_Creator.createLog("Password incorreta")));

        LogCategory dbCategory = new LogCategory("base-de-dados");
        dbCategory.add(new LogEntry(Info_Creator.createLog("Conexão estabelecida")));
        dbCategory.add(new LogEntry(Warning_Creator.createLog("Query lenta detetada")));

        LogCategory appLogs = new LogCategory("aplicacao");
        appLogs.add(authCategory);   // composite dentro de composite
        appLogs.add(dbCategory);
        appLogs.add(new LogEntry(Error_Creator.createLog("Erro genérico da aplicação"))); // leaf direto

        System.out.println("\nOutput estruturado por categorias:");
        ConsoleDestination console = new ConsoleDestination();
        appLogs.outputTo(console);
    }
}


/*
Configuração centralizada (Singleton): [X]
- LogConfig - instância única com as configurações globais do sistema de logs (nível mínimo, destino, formato)

Enum auxiliar: [X]
- LogLevel - define os níveis de severidade possíveis: DEBUG, INFO, WARNING, ERROR

Produto abstrato: [X]
- LogRecord - contrato base de um registo de log (mensagem, timestamp, nível, formato)

Produtos concretos:
- InfoLog - registo de nível INFO [X]
- WarningLog - registo de nível WARNING [X]
- ErrorLog - registo de nível ERROR [X]
- DebugLog - registo de nível DEBUG [X]

Criador interface: [X]
- LogCreator - declara o factory method createLog() que as subclasses implementam [X]

Criadores concretos:
- InfoLogCreator - cria instâncias de InfoLog [X]
- WarningLogCreator - cria instâncias de WarningLog [X]
- ErrorLogCreator - cria instâncias de ErrorLog [X]
- DebugLogCreator - cria instâncias de DebugLog [X]
*/