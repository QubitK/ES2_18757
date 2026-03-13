package com.es2.bridgepattern;
import java.util.LinkedHashMap;

public class APIMoodle implements APIServiceInterface {
    protected LinkedHashMap<String, String> content;

    public APIMoodle(){
        this.content = new LinkedHashMap<>(); // inits the LinkedHashMap
    }

    @Override
    public String getContent(String contentId){
        // verifies if contentId is 0 to return all contents concatenated
        if(contentId.equals("0")){
            if(content.isEmpty()) return null;
            StringBuilder allContentConcatenated = new StringBuilder();
            for(String value : content.values()) { //iterates the 2nd element of each pair
                allContentConcatenated.append(value);
            }
            return allContentConcatenated.toString();
        }
        return content.getOrDefault(contentId, null);
        // same as:
        /*
        if (content.containsKey(contentId)) {
            return content.get(contentId);
        } else {
            return null;
        }*/
    }

    @Override
    public String setContent(String content){
        String contentId = String.valueOf(this.content.size() + 1); // incremental ids staritng in 1
        this.content.put(contentId, content);
        return contentId;
    }
}
