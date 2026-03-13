package com.es2.loggingproject;

public enum LogConfig {

    INSTANCE;

    // nível minimo para o registo efetivo do Log
    private LogLevel logMinLevel = LogLevel.DEBUG; // define with ENUM possible value in LogLevel.java
    // para onde o log é enviado (consola, ficheiro, base de dados, serviço remoto, etc)
    private String logDestination = "CONSOLE";
    // template de formatação de cada mensagem de log.
    private String logMsgFormat = "[{level}] [{timestamp}]: {message}";

    // getters e setters
    public LogLevel getMinimumLevel() { return logMinLevel; }
    public void setMinimumLevel(LogLevel minimumLevel) { this.logMinLevel = minimumLevel; }

    public String getDestination() { return logDestination; }
    public void setDestination(String destination) { this.logDestination = destination; }

    public String getMessageFormat() { return logMsgFormat; }
    public void setMessageFormat(String messageFormat) { this.logMsgFormat = messageFormat; }
}