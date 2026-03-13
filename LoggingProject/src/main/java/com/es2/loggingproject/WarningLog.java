package com.es2.loggingproject;

public class WarningLog extends LogRecord {

    protected WarningLog(String message){
        super(LogLevel.WARNING, message);
    }

}
