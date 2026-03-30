package com.es2.loggingproject.M2_factory;

import com.es2.loggingproject.M1_config.LogLevel;

public class DebugLog extends LogRecord {

    protected DebugLog(String message){
        super(LogLevel.DEBUG, message);
    }

}
