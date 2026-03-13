package com.es2.compositepattern;

// LEAF

public class Link extends Menu {

    String URL;

    public Link(String label, String url){
        super(label);
        this.URL = url;
    }

    public String getURL(){ // Gets the link's URL
        return this.URL;
    }

    public void setURL(String url){
        this.URL = url;
    }

    @Override
    public void showOptions(String indent){{
        System.out.println(indent + "<li><a href=\"" + this.URL + "\">" + this.label + "</a></li>");
    }

    }

}

// <ul>
//  <li>Coffee</li>
//  <li>Tea
//        <ul>
//            <li>Black tea</li>
//            <li>Green tea</li>
//    </ul>
//  </li>
//  <li>Milk</li>
//</ul>