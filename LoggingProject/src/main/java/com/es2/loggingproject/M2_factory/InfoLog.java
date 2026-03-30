package com.es2.loggingproject.M2_factory;

import com.es2.loggingproject.M1_config.LogLevel;

public class InfoLog extends LogRecord {

    protected InfoLog(String message){
        super(LogLevel.INFO, message);
    }

}