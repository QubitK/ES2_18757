package com.es2.loggingproject.M5_object_pool_destination;

import com.es2.loggingproject.M3_bridge_destination.FileDestination;
import com.es2.loggingproject.M3_bridge_destination.LogDestinationInterface;
import com.es2.loggingproject.M4_composite_category.LogCategory;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

// Constructor creates as much destination files for each LogCategory passed as param via getInstance

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

    public synchronized void close() throws IOException {
        for (LogDestinationInterface dest : available) {
            if (dest instanceof FileDestination fd) fd.close();
        }
        for (LogDestinationInterface dest : inUse) {
            if (dest instanceof FileDestination fd) fd.close();
        }
    }

    // Fecha todos os FileWriter abertos e permite reinicialização do Singleton
    public static synchronized void reset() throws IOException {
        if (instance == null) return;
        instance.close();
        instance.available.clear();
        instance.inUse.clear();
        instance = null;
    }



}
