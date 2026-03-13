package com.es2.loggingproject;

public class WarningLogCreator extends LogCreator {

    @Override
    public LogRecordInterface createLog(String message){
        return new WarningLog(message);
    }

}
