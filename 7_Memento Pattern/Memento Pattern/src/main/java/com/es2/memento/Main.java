package com.es2.memento;

/*
Server -> Originator: objeto que cria e interpreta os estados armazenados em objetos Memento
BackupService -> Caretaker: solicita a armazena objetos Memento sem nunca ler os seus conteúdos
 */

public class Main {
    public static void main(String[] args) throws NotExistingSnapshotException, ExistingStudentException {
        Server s = new Server();
        BackupService backup = new BackupService(s);

        backup.takeSnapshot();
        s.addStudent("Maria José");
        backup.takeSnapshot();
        s.addStudent("Manuel António");
        System.out.println(s.getStudentNames().size());

        backup.restoreSnapshot(1);
        System.out.println(s.getStudentNames().size());
    }
}
