package com.es2.loggingproject;

public class LogEntry implements LogComponent {
    private final LogRecordInterface record;

    public LogEntry(LogRecordInterface record) {
        this.record = record;
    }

    @Override
    public String getName() {
        return record.getLevel().toString();
    }

    @Override
    public void outputTo(LogDestinationInterface destination) {
        if (record.getLevel().ordinal() >= LogConfig.INSTANCE.getMinimumLevel().ordinal()) {
            destination.write(record);
        }
    }
}