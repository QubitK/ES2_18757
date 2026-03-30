package com.es2.loggingproject.M3_bridge_destination;

import com.es2.loggingproject.M2_factory.LogRecordInterface;

public class ConsoleDestination implements LogDestinationInterface {

    @Override
    public void write(LogRecordInterface log) {
        System.out.println(log.outputFormatted());
    }
}
