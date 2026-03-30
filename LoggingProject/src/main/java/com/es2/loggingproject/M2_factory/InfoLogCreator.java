package com.es2.loggingproject.M2_factory;

public class InfoLogCreator extends LogCreator {

    @Override
    public LogRecordInterface createLog(String message) {
        return new InfoLog(message);
    }

}