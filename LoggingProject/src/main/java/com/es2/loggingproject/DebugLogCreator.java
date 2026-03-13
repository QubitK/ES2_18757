package com.es2.loggingproject;

public class DebugLogCreator extends LogCreator {

    @Override
    public LogRecordInterface createLog(String message) {
        return new DebugLog(message);
    }
}