package com.es2.loggingproject;

import java.io.FileWriter;
import java.io.IOException;

public class FileDestination implements LogDestinationInterface {

    private final String filePath;
    private final FileWriter writer;

    public FileDestination(String filePath) throws IOException {
        this.filePath = filePath;
        this.writer   = new FileWriter(filePath, true); // persistent FileWrite
    }

    @Override
    public void write(LogRecordInterface log) {
        try {
            writer.write(log.outputFormatted() + "\n");
            writer.flush();
        } catch (IOException e) {
            System.err.println("Erro ao escrever em " + filePath + ": " + e.getMessage());
        }
    }

    public void close() throws IOException {
        writer.close();
    }
}