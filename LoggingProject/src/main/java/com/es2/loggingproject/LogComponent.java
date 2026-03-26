package com.es2.loggingproject;
// COMPOSITE PATTERN: COMPONENT

public interface LogComponent {
    String getName();
    void outputTo(LogDestinationInterface destination);
}