package com.es2.loggingproject.M6_memento_system_backup;
import com.es2.loggingproject.M1_config.LogConfig;
import com.es2.loggingproject.M1_config.LogLevel;
import com.es2.loggingproject.M3_bridge_destination.FileDestination;
import com.es2.loggingproject.M3_bridge_destination.LogDestinationInterface;
import com.es2.loggingproject.M3_bridge_destination.Logger;
import com.es2.loggingproject.M4_composite_category.LogCategory;
import com.es2.loggingproject.M4_composite_category.LogComponent;
import com.es2.loggingproject.M5_object_pool_destination.DestinationPool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// MEMENTO PATTERN: ORIGINATOR
public class LogSystemOriginator {

    private final Logger logger;
    private List<LogCategory> rootCategories;

    public LogSystemOriginator(Logger logger, List<LogCategory> rootCategories) {
        this.logger         = logger;
        this.rootCategories = rootCategories;
    }

    // Cria um snapshot do estado actual (criando e devolve Memento)
    public LogSystemMemento backup() {
        LogLevel currentLevel = LogConfig.INSTANCE.getMinimumLevel();

        // Extrai id -> filePath de cada FileDestination activa no Logger
        HashMap<String, String> destinationPaths = new HashMap<>();
        for (Map.Entry<String, LogDestinationInterface> entry : logger.getDestinations().entrySet()) {
            if (entry.getValue() instanceof FileDestination fd) {
                destinationPaths.put(entry.getKey(), fd.getFilePath());
            }
        }

        // Percorre a árvore de categorias ignorando LogEntry (só nós LogCategory)
        List<LogCategory> categoriesSnapshot = deepCopyCategories(rootCategories);

        return new LogSystemMemento(currentLevel, destinationPaths, categoriesSnapshot);
    }

    // Restaura o estado a partir de um Memento
    public void restore(LogSystemMemento memento) throws IOException {
        // 1. Restaura nível mínimo
        LogConfig.INSTANCE.setMinimumLevel(memento.getMinLevel());

        // 2. Reconstrói destinations no Logger
        logger.clearDestinations();
        for (Map.Entry<String, String> entry : memento.getDestinationPaths().entrySet()) {
            logger.addDestinationWithId(entry.getKey(), new FileDestination(entry.getValue()));
        }

        // 3. Restaura categorias
        rootCategories = memento.getCategories();

        // 4. Reinicializa o pool com as categorias restauradas
        DestinationPool.reset();
        DestinationPool.getInstance(rootCategories);
    }

    public List<LogCategory> getRootCategories() {
        return rootCategories;
    }

    // Percorre recursivamente e copia só os nós LogCategory, ignorando LogEntry
    private List<LogCategory> deepCopyCategories(List<LogCategory> source) {
        List<LogCategory> result = new ArrayList<>();
        for (LogCategory category : source) {
            LogCategory copy = new LogCategory(category.getName());
            for (LogComponent child : category.getChildren()) {
                if (child instanceof LogCategory childCategory) {
                    LogCategory childCopy = deepCopyCategories(List.of(childCategory)).get(0);
                    copy.add(childCopy);
                }
                // LogEntry é ignorado — não faz parte do estado estrutural a preservar
            }
            result.add(copy);
        }
        return result;
    }
}