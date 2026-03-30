package com.es2.loggingproject.M4_composite_category;

import com.es2.loggingproject.M3_bridge_destination.LogDestinationInterface;

// COMPOSITE PATTERN: COMPONENT
public abstract class LogComponent {

    public abstract void outputTo(LogDestinationInterface destination);

}