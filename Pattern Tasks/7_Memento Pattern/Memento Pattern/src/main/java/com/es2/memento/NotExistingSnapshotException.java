package com.es2.memento;

// thrown when the snapshot doesn't exist
public class NotExistingSnapshotException extends Exception {
    public NotExistingSnapshotException(String s){
        super(s);
    }
}
