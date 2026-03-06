package com.es2.bridge;

import java.util.LinkedHashMap;

/**
 * Implementor interface of the Bridge Pattern.
 * Defines the operations that concrete service implementations must provide
 * for storing and retrieving content.
 */
public interface APIServiceInterface {

    /**
     * Stores a value and returns its auto-generated identifier.
     *
     * @param value the content to store
     * @return the identifier assigned to the stored content
     */
    // Stors a value and returns its auto-generated identifier
    String store(String value);

    /**
     * Retrieves the value associated with the given identifier.
     *
     * @param id the content identifier
     * @return the stored value, or null if not found
     */
    String get(String id);

    /**
     * Returns all stored contents, preserving insertion order.
     *
     * @return a LinkedHashMap of all id-value pairs
     */
    LinkedHashMap<String, String> getAll();
}
