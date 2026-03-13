package com.es2.loggingproject;

public class ErrorLogCreator extends LogCreator {

    @Override
    public LogRecordInterface createLog(String message){
        return new ErrorLog(message);
    }

}
