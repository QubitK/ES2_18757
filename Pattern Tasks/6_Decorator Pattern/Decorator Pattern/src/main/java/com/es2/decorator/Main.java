package com.es2.decorator;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Objetos criados na seguinte ordem (de dentro para fora) :
        // Auth() criado -> CommonWordsValidator(auth) recebe auth e guarda em wrapped -> Logging(commonWords) recebe CommonWordsValidator e guarda também em
        AuthInterface service = new Logging(new CommonWordsValidator(new Auth())); // Mas depois são executados pela ordem inversa: Logging -> CommonWordsValidator -> Auth
        try {
            service.auth("admin", "admin"); // [CWV] NOK - password comum e insegura, catched exception
//            service.auth("user", "LisbonPW"); // All Classes OK
//            service.auth("user2", "password2"); // [AUTH] NOK - credenciais não existem, throws exception
        } catch (AuthException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
