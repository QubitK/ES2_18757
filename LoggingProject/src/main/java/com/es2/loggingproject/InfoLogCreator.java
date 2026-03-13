package com.es2.loggingproject;

public class InfoLogCreator extends LogCreator {

    @Override
    public LogRecordInterface createLog(String message) {
        return new InfoLog(message);
    }

}