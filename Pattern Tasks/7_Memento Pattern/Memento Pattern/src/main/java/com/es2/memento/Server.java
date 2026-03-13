package com.es2.memento;
import java.util.ArrayList;

// Stores student names.
public class Server {

    private ArrayList<String> students;

    public Server(){
        students = new ArrayList<>();
    }

    // Adds a new student name
    public void addStudent(String studentName) throws ExistingStudentException {
        if (students.contains(studentName)) {
            throw new ExistingStudentException("Student " + studentName + " already exists.");
        }
        students.add(studentName);
    }

    // Backups the server state to a Memento object (Caretaker)
    public Memento backup(){
        return new Memento(students);
    }

    // Restores a previous server state
    public void restore(Memento state){
        students = new ArrayList<>(state.getState());
    }

    // Return the student names
    public ArrayList getStudentNames(){
        return students;
    }

}
