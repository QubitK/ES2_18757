package com.es2.loggingproject;

public class ErrorLog extends LogRecord {

    protected ErrorLog(String message){
        super(LogLevel.ERROR, message);
    }

}
