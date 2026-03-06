package com.es2.composite;

public class Link extends Menu {

    private String url;

    public Link() {
        super("");
        this.url = "";
    }

    public Link(String name, String url) {
        super(name);
        this.url = url;
    }

    public void setLabel(String label) {
        this.name = label;
    }

    public void setURL(String url) {
        this.url = url;
    }

    public String getURL() {
        return url;
    }

    @Override
    public String getHTML() {
        return "<a href=\"" + url + "\">" + name + "</a>";
    }

    @Override
    public void showOptions() {
        System.out.println(name);
        System.out.println(url);
    }
}
