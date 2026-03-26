package com.es2.loggingproject;
import java.io.FileWriter;
import java.io.IOException;

public class FileDestination implements LogDestinationInterface {

    private final String filePath;
    public FileDestination (String filePath){
        this.filePath = filePath;
    }

    @Override
    public void write(LogRecordInterface log) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(log.outputFormatted() + "\n");
        } catch (IOException e) {
            System.err.println("Erro ao escrever no ficheiro: " + e.getMessage());
        }
    }
}
