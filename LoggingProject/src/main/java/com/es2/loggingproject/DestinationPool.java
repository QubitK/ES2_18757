package com.es2.loggingproject;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class DestinationPool {

    private static DestinationPool instance;

    private final LinkedList<LogDestinationInterface> available;
    private final LinkedList<LogDestinationInterface> inUse;

    private DestinationPool(List<LogCategory> categories) throws IOException {
        available = new LinkedList<>();
        inUse     = new LinkedList<>();
        for (LogCategory category : categories) {
            available.add(new FileDestination(category.getName() + "Log.txt"));
        }
    }

    public static synchronized DestinationPool getInstance(List<LogCategory> categories) throws IOException {
        if (instance == null) {
            instance = new DestinationPool(categories);
        }
        return instance;
    }

    public synchronized LogDestinationInterface acquire() throws PoolExhaustedException {
        if (available.isEmpty()) {
            throw new PoolExhaustedException("Pool esgotado: sem destinos disponíveis.");
        }
        LogDestinationInterface dest = available.removeFirst();
        inUse.add(dest);
        return dest;
    }

    public synchronized void release(LogDestinationInterface dest) throws ObjectNotFoundException {
        if (!inUse.remove(dest)) {
            throw new ObjectNotFoundException("Destino não pertence a este pool.");
        }
        available.add(dest);
    }
}