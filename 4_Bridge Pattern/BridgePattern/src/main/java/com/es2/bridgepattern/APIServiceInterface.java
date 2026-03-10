package com.es2.bridgepattern;

public interface APIServiceInterface {

    // JAVA <interface> can't have its own fields, only methods to be overwritten

    String getContent(String contentId);

    String setContent(String content);

}
