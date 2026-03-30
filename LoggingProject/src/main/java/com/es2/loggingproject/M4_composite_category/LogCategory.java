package com.es2.loggingproject.M4_composite_category;

// COMPOSITE PATTERN: COMPOSITE
import com.es2.loggingproject.M3_bridge_destination.LogDestinationInterface;

import java.util.ArrayList;
import java.util.List;

public class LogCategory extends LogComponent {

    private final String name;
    private final List<LogComponent> children = new ArrayList<>();

    public LogCategory(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public void add(LogComponent component)    { children.add(component); }
    public void remove(LogComponent component) { children.remove(component); }
    public List<LogComponent> getChildren()    { return children; }

    @Override
    public void outputTo(LogDestinationInterface destination) {
        for (LogComponent child : children) {
            child.outputTo(destination);
        }
    }

}