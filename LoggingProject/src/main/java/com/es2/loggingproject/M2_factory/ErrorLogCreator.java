package com.es2.loggingproject.M2_factory;

public class ErrorLogCreator extends LogCreator {

    @Override
    public LogRecordInterface createLog(String message){
        return new ErrorLog(message);
    }

}
