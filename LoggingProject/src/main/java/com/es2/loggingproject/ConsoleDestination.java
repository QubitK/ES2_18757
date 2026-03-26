package com.es2.loggingproject;

public class ConsoleDestination implements LogDestinationInterface {

    @Override
    public void write(LogRecordInterface log) {
        System.out.println(log.outputFormatted());
    }
}
