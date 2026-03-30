package com.es2.loggingproject.M6_memento_system_backup;

import java.io.IOException;
import java.util.ArrayList;

// MEMENTO PATTERN: CARETAKER
public class LogSystemCaretaker {

    private final LogSystemOriginator originator;
    private final ArrayList<LogSystemMemento> snapshots = new ArrayList<>();

    public LogSystemCaretaker(LogSystemOriginator originator) {
        this.originator = originator;
    }

    public void takeSnapshot() {
        snapshots.add(originator.backup());
        System.out.println("[Caretaker] Snapshot guardado. Total: " + snapshots.size());
    }

    public void restoreSnapshot(int snapshotIndex) throws IOException {
        if (snapshotIndex < 0 || snapshotIndex >= snapshots.size()) {
            throw new IndexOutOfBoundsException("Snapshot " + snapshotIndex + " não existe.");
        }
        originator.restore(snapshots.get(snapshotIndex));
        System.out.println("[Caretaker] Snapshot " + snapshotIndex + " restaurado.");
    }

    public int size() {
        return snapshots.size();
    }
}