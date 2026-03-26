package com.es2.loggingproject;

import java.util.ArrayList;
import java.util.List;

public class LogCategory implements LogComponent {
    private final String name;
    private final List<LogComponent> children = new ArrayList<>();

    public LogCategory(String name) {
        this.name = name;
    }

    public void add(LogComponent component)    { children.add(component); }
    public void remove(LogComponent component) { children.remove(component); }
    public List<LogComponent> getChildren()    { return children; }

    @Override
    public String getName() { return name; }

    @Override
    public void outputTo(LogDestinationInterface destination) {
        System.out.println("[CATEGORY: " + name + "]");
        for (LogComponent child : children) {
            child.outputTo(destination);
        }
    }
}