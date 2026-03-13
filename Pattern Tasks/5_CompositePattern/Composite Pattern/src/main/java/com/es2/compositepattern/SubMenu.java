package com.es2.compositepattern;
import java.util.ArrayList;
import java.util.List;

// COMPOSITE: armazena elementos e delega tarefas

public class SubMenu extends Menu {

//  - List é uma interface — define o que se pode fazer (add, remove, get, size...)
//  - ArrayList é uma classe concreta — define como é feito internamente (array dinâmico)
    private List<Menu> children = new ArrayList<>(); // children é do tipo List, mas o objeto criado em memória é um ArrayList

    public SubMenu(String label){
        super(label);
    }

    public void addChild(Menu child) { // Adds a child element (link | submenu) to the menu
        children.add(child);
    }

    public void removeChild(Menu child){ // Removes a child element (link | submenu)
        children.remove(child);
    }

    @Override
    public void showOptions(String indent) {
        System.out.println(indent + "<li>" + this.label);
        System.out.println(indent + "  <ol>");
        for (Menu child : children) {
            child.showOptions(indent + "  ");  // aumenta indentação a cada nível
        }
        System.out.println(indent + "  </ol>");
        System.out.println(indent + "</li>");
    }


}
