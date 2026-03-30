package com.es2.loggingproject.M3_bridge_destination;

import com.es2.loggingproject.M2_factory.LogRecordInterface;

import java.io.FileWriter;
import java.io.IOException;

public class FileDestination implements LogDestinationInterface {

    private final String filePath;
    private final FileWriter writer;

    public FileDestination(String filePath) throws IOException {
        this.filePath = filePath;
        this.writer   = new FileWriter(filePath, true); // persistent FileWrite
    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public void write(LogRecordInterface log) {
        try {
            writer.write(log.outputFormatted() + "\n");
            writer.flush();
        } catch (IOException e) {
            System.err.println("Erro ao escrever no ficheiro: " + e.getMessage());
        }
    }

    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            System.err.println("Erro ao fechar FileDestination: " + e.getMessage());
        }
    }
}