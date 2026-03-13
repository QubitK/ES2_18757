package com.es2.loggingproject;

public class DebugLog extends LogRecord {

    protected DebugLog(String message){
        super(LogLevel.DEBUG, message);
    }

}
