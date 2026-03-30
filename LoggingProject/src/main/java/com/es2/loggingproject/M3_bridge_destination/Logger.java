package com.es2.loggingproject.M3_bridge_destination;
import com.es2.loggingproject.M1_config.LogConfig;
import com.es2.loggingproject.M2_factory.LogRecordInterface;

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

    // Usado pelo Originator no restore — preserva o id original do snapshot
    public void addDestinationWithId(String id, LogDestinationInterface destination) {
        destinations.put(id, destination);
    }

    public void removeDestination(String id) {
        destinations.remove(id);
    }

    // Limpa todas as destinations — usado pelo Originator antes do restore
    public void clearDestinations() {
        destinations.clear();
    }

    // Expõe o mapa para o Originator poder ler durante o backup
    public HashMap<String, LogDestinationInterface> getDestinations() {
        return destinations;
    }

    public void logAllDestinations(LogRecordInterface record) {
        if (meetsMinLevel(record)) {
            for (LogDestinationInterface destination : destinations.values()) {
                destination.write(record);
            }
        }
    }

    public void logDestination(String destination_id, LogRecordInterface record) {
        LogDestinationInterface destination = destinations.getOrDefault(destination_id, null);
        if (destination == null) return;
        if (meetsMinLevel(record)) {
            destination.write(record);
        }
    }
}