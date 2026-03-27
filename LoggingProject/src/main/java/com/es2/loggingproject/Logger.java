package com.es2.loggingproject;
import java.util.HashMap;

public class Logger {
    private final HashMap<String, LogDestinationInterface> destinations;

    public Logger() {
        this.destinations = new HashMap<>();
    }

    private boolean meetsMinLevel(LogRecordInterface record) {
        return record.getLevel().ordinal() >= LogConfig.INSTANCE.getMinimumLevel().ordinal();
    }

    public String addDestination(LogDestinationInterface destination) {
        String id = String.valueOf(destinations.size() + 1);
        destinations.put(id, destination);
        return id;
    }

    public void removeDestination(String id) {
        destinations.remove(id);
    }

    public void logAllDestinations(LogRecordInterface record){
        if(meetsMinLevel(record)){
            for(LogDestinationInterface destination : destinations.values()){
                destination.write(record);
            }
        }
    }

    public void logDestination(String destination_id, LogRecordInterface record){
        LogDestinationInterface destination = destinations.getOrDefault(destination_id, null);
        if(destination == null) return;
        if(meetsMinLevel(record)){
            destination.write(record);
        }
    }
}
