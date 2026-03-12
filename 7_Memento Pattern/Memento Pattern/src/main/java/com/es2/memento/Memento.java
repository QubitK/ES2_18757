package com.es2.memento;
import java.util.ArrayList;

// Stores a snapshot of the Server state

public class Memento {
    // ArrayList with the student names
    ArrayList<String> studentNames;

    // The constructor stores the student names stored by the Server
    public Memento(ArrayList<String> studentNames){ // copy constructor
        this.studentNames = new ArrayList<>(studentNames);
    }

    // Return the Server state stored by the memento
    public ArrayList<String> getState(){
        return this.studentNames;
    }

}
