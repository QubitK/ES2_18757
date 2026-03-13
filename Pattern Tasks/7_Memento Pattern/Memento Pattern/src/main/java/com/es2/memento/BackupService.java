package com.es2.memento;
import java.util.ArrayList;

//This class allows the creation and storage of Server state snapshots encapsulated in a Memento object

public class BackupService {

    Server server;
    private ArrayList<Memento> snapshots;

    public BackupService(Server server){
        this.server = server;
        snapshots = new ArrayList<>();
    }

    // Takes a Server snapshot and stores it inside a Memento object
    public void takeSnapshot(){
        snapshots.add(server.backup());
    }

    // snapshotNumber: the snapshot number between 0 and (number of snapshots - 1)
    public void restoreSnapshot(int snapshotNumber) throws NotExistingSnapshotException {
        if (snapshotNumber < 0 || snapshotNumber >= snapshots.size()) {
            throw new NotExistingSnapshotException("Snapshot " + snapshotNumber + " does not exist.");
        }
        server.restore(snapshots.get(snapshotNumber));
    }

}
