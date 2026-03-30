package com.es2.loggingproject.M4_composite_category;

import com.es2.loggingproject.M1_config.LogLevel;
import com.es2.loggingproject.M3_bridge_destination.LogDestinationInterface;

// M6 COMPOSITE PATTERN: COMPONENT
// M6 + M6 : COMPOSITE + DECORATOR
public abstract class LogComponent {

    public abstract void outputTo(LogDestinationInterface destination);

    // método necessário para MonitoringDecorator contar por nível sem casting
    public abstract LogLevel getLevel();

}