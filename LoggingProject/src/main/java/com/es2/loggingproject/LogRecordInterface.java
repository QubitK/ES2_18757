package com.es2.loggingproject;

public interface LogRecordInterface {

    LogLevel getLevel();
    String getMessage();
    String getTimestamp();
    String format();
    void outputLog();

}