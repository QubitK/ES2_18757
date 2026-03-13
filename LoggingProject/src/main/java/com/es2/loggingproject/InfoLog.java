package com.es2.loggingproject;

public class InfoLog extends LogRecord {

    protected InfoLog(String message){
        super(LogLevel.INFO, message);
    }

}