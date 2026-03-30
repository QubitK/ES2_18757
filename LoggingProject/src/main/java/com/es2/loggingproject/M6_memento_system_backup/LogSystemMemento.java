package com.es2.loggingproject.M6_memento_system_backup;

import com.es2.loggingproject.M1_config.LogLevel;
import com.es2.loggingproject.M4_composite_category.LogCategory;

import java.util.HashMap;
import java.util.List;

// MEMENTO PATTERN: MEMENTO
public class LogSystemMemento {

    private final LogLevel minLevel;
    private final HashMap<String, String> destinationPaths; // id -> filePath
    private final List<LogCategory> categories;             // árvore só com nós LogCategory

    public LogSystemMemento(LogLevel minLevel,
                            HashMap<String, String> destinationPaths,
                            List<LogCategory> categories) {
        this.minLevel         = minLevel;
        this.destinationPaths = destinationPaths;
        this.categories       = categories;
    }

    public LogLevel getMinLevel() {
        return minLevel;
    }

    public HashMap<String, String> getDestinationPaths() {
        return destinationPaths;
    }

    public List<LogCategory> getCategories() {
        return categories;
    }
}