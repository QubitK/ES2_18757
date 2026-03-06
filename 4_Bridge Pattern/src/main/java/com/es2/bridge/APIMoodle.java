package com.es2.bridge;

import java.util.LinkedHashMap;

/**
 * Moodle's service implementation (ConcreteImplementor in the Bridge Pattern).
 * Stores content in a LinkedHashMap to preserve insertion order.
 * Each stored value receives an auto-generated sequential identifier.
 */
public class APIMoodle implements APIServiceInterface {

    private final LinkedHashMap<String, String> contents = new LinkedHashMap<>();
    private int counter = 0;

    @Override
    public String store(String value) {
        String id = String.valueOf(counter++);
        contents.put(id, value);
        return id;
    }

    @Override
    public String get(String id) {
        return contents.get(id);
    }

    @Override
    public LinkedHashMap<String, String> getAll() {
        return contents;
    }
}
