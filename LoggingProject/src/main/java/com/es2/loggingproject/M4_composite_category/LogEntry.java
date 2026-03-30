package com.es2.loggingproject.M4_composite_category;

import com.es2.loggingproject.M1_config.LogConfig;
import com.es2.loggingproject.M2_factory.LogRecordInterface;
import com.es2.loggingproject.M3_bridge_destination.LogDestinationInterface;

// COMPOSITE PATTERN: LEAF
public class LogEntry extends LogComponent {

    private final LogRecordInterface record;

    public LogEntry(LogRecordInterface record) {
        this.record = record;
    }

    public String getName() {
        return record.getLevel().name();
    }

    @Override
    public void outputTo(LogDestinationInterface destination) {
        if (record.getLevel().ordinal() >= LogConfig.INSTANCE.getMinimumLevel().ordinal()) {
            destination.write(record);
        }
    }

}