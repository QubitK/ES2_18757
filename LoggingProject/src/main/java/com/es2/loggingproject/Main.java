package com.es2.loggingproject;

public class Main {
    public static void main(String[] args) {

        System.out.println("---------------------------- ES2: LOGGING PROJECT ----------------------------\n");
        LogConfig.INSTANCE.setMinimumLevel(LogLevel.DEBUG);

        LogCreator debugCreator_1 = new DebugLogCreator();
        LogCreator errorCreator_1 = new ErrorLogCreator();
        LogCreator infoCreator_1 = new InfoLogCreator();
        LogCreator warningCreator_1 = new WarningLogCreator();

        LogRecordInterface debugLogger_1 = debugCreator_1.createLog("DEBUG test message 1");
        LogRecordInterface errorLogger_1 = errorCreator_1.createLog("ERROR test message 1");
        LogRecordInterface infoLogger_1 = infoCreator_1.createLog("INFO test message 1");
        LogRecordInterface warningLogger_1 = warningCreator_1.createLog("WARNING test message 1");
        LogRecordInterface debugLogger_2 = debugCreator_1.createLog("DEBUG test message 2");

        debugLogger_1.outputLog();
        errorLogger_1.outputLog();
        infoLogger_1.outputLog();
        warningLogger_1.outputLog();
        debugLogger_2.outputLog();

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