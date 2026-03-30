package com.es2.loggingproject.M3_bridge_destination;

import com.es2.loggingproject.M2_factory.LogRecordInterface;

public interface LogDestinationInterface {
    void write(LogRecordInterface log);
}
