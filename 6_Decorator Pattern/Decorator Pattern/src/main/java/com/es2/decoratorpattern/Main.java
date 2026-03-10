package com.es2.decoratorpattern;

import java.io.IOException;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // Objetos criados na seguinte ordem (de dentro para fora) :
        // Auth() criado -> CommonWordsValidator(auth) recebe auth e guarda em wrapped -> Logging(commonWords) recebe CommonWordsValidator e guarda também em
        AuthInterface service = new Logging(new CommonWordsValidator(new Auth())); // Mas depois são executados pela ordem inversa: Logging -> CommonWordsValidator -> Auth
        try {
            service.auth("user", "LisbonPW"); // All Classes OK
            service.auth("admin", "admintelecom"); // [CWV] NOK - password comum e insegura
//            service.auth("user3", "password3"); // [AUTH] NOK - credenciais não existem
        } catch (AuthException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
