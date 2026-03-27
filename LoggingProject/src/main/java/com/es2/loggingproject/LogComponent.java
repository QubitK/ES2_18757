package com.es2.loggingproject;

// COMPOSITE PATTERN: COMPONENT
public abstract class LogComponent {

    public abstract void outputTo(LogDestinationInterface destination);

}