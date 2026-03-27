package com.es2.loggingproject;

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