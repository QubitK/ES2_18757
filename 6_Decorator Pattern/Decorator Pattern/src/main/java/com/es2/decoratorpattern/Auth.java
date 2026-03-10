package com.es2.decoratorpattern;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// Implements the main authentication logic

public class Auth implements AuthInterface {

    private String hash(String pw){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashed = md.digest(pw.getBytes());
            StringBuilder sb = new StringBuilder();
            for(byte b : hashed){
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    }

    private static final Map<String, String> REGISTERED_CREDENTIALS = new HashMap<>();
    {
        REGISTERED_CREDENTIALS.put("user", hash("LisbonPW"));
        REGISTERED_CREDENTIALS.put("admin", hash("admintelecom"));
        REGISTERED_CREDENTIALS.put("user2", hash("password2"));
    }

    public Auth(){}


    // AuthException - thrown when authentication fails
    public void auth(String username, String password) throws AuthException {
        String hashedPW = hash(password);
        if(REGISTERED_CREDENTIALS.containsKey(username) && REGISTERED_CREDENTIALS.get(username).equals(hashedPW)) {
            System.out.println("[AUTH OK] Acesso verificado!");
        } else {
            throw new AuthException("[AUTH NOK] Invalid credentials.");
        }
    }
}
