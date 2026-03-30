package com.es2.loggingproject.M2_factory;

import com.es2.loggingproject.M1_config.LogLevel;

public class WarningLog extends LogRecord {

    protected WarningLog(String message){
        super(LogLevel.WARNING, message);
    }

}
