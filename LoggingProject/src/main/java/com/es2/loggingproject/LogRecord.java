package com.es2.loggingproject;

import java.time.LocalDateTime;

// Classe de abstração da interface
public abstract class LogRecord implements LogRecordInterface {

    protected final LogLevel level;
    protected final String message;
    protected final String timestamp;

    protected LogRecord(LogLevel level, String message) {
        this.message = message;
        this.level = level;
        this.timestamp = LocalDateTime.now().toString();
    }

    @Override public LogLevel getLevel()   { return level; }
    @Override public String getMessage()   { return message; }
    @Override public String getTimestamp() { return timestamp; }

    @Override
    public String format() {
        return LogConfig.INSTANCE.getMessageFormat()
                .replace("{level}", getLevel().toString())
                .replace("{timestamp}", timestamp)
                .replace("{message}",   message);
    }

    public void outputLog() {
        if(this.level.ordinal() >= LogConfig.INSTANCE.getMinimumLevel().ordinal()){
            System.out.println(this.format() + "\n");
        }
    }

}