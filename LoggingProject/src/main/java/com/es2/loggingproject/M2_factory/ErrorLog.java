package com.es2.loggingproject.M2_factory;

import com.es2.loggingproject.M1_config.LogLevel;

public class ErrorLog extends LogRecord {

    protected ErrorLog(String message){
        super(LogLevel.ERROR, message);
    }

}
