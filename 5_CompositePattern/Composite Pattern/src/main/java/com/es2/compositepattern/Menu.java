package com.es2.compositepattern;

// COMPONENT: classe abstrata que garante polimorfismo para SubMenu e Link

public abstract class Menu {
    String label;

    public Menu(String label){
        this.label = label;
    }

    public String getLabel(){
        return label;
    }

    public void getLabel(String label){
        this.label = label;
    }

    public abstract void showOptions(String indent); // Print the combination of label and URL

}
