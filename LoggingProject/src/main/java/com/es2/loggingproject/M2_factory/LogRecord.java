package com.es2.loggingproject.M2_factory;

import com.es2.loggingproject.M1_config.LogConfig;
import com.es2.loggingproject.M1_config.LogLevel;

import java.time.LocalDateTime;

// Classe de abstração da interface
public abstract class LogRecord implements LogRecordInterface {

    protected LogLevel level;
    protected String message;
    protected String timestamp;

    protected LogRecord(LogLevel level, String message) {
        this.message = message;
        this.level = level;
        this.timestamp = LocalDateTime.now().toString();
    }

    @Override public LogLevel getLevel()   { return level; }
    @Override public String getMessage()   { return message; }
    @Override public String getTimestamp() { return timestamp; }

    @Override
    public String outputFormatted() {
        return LogConfig.INSTANCE.getMessageFormat()
                .replace("{level}", getLevel().toString())
                .replace("{timestamp}", timestamp)
                .replace("{message}",   message);
    }
}