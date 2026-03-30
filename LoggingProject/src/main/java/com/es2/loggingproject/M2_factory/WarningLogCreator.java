package com.es2.loggingproject.M2_factory;

public class WarningLogCreator extends LogCreator {

    @Override
    public LogRecordInterface createLog(String message){
        return new WarningLog(message);
    }

}
