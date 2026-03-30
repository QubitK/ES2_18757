package com.es2.loggingproject.M2_factory;

import com.es2.loggingproject.M1_config.LogLevel;

public interface LogRecordInterface {

    LogLevel getLevel();
    String getMessage();
    String getTimestamp();
    String outputFormatted();
}