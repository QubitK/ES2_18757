package com.es2.decoratorpattern;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

// Validates if the password exists in a database of english words, to avoid the use of common words.

public class CommonWordsValidator extends Decorator {

    public CommonWordsValidator(AuthInterface auth){
        super(auth);
    }

    public void auth(String username, String password) throws AuthException, IOException{
        String response = getHTTPRequest(password);
        if(!response.isEmpty()){
            throw new AuthException("[CWValidator] Password é palavra comum e insegura!");
        }
        System.out.println("[CWValidator] Password OK!");
        wrapped.auth(username, password);
    }

    // Establishes a HTTP Request with the server
    public String getHTTPRequest(String word) throws IOException {
        // API RESPONSE SIMULATION CODE
        String result = "";
        Set<String> commonPasswords = new HashSet<>(Arrays.asList(
                "123456", "admin", "12345678", "123456789", "12345",
                "password", "Aa123456", "1234567890", "Pass@123", "admin123",
                "1234567", "123123", "111111", "12345678910", "P@ssw0rd",
                "Password", "Aa@123456", "admintelecom", "Admin@123", "112233"
        ));
        if (commonPasswords.contains(word)) {
            result =  "Common password detected" + word;
        }
        return result;

//        StringBuilder result = new StringBuilder(); // StringBuilder to accumulate response body
//        // wraps the target address(word) in a URL object
//        URL url = new URL("https://owlbot.info/api/v0/dictionary/" + word + "?format=json");
//        // open Http connection with url and store it as a HttpURLConnection object: conn
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        // set request headers
//        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-GB;     rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13 (.NET CLR 3.5.30729)");
//        conn.setRequestMethod("GET"); // set GET request method
//        //  open buffered reader on the response stream and executes the request
//        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//        String line;
//        // String line to iterate through the buffer content and add to the result StringBuilder
//        while((line = rd.readLine()) != null){
//            result.append(line);
//        }
//        rd.close(); // close buffer
//        return result.toString(); // return answer as String
    }
}
