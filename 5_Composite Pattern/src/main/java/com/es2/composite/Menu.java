package com.es2.composite;

// É preferencial criar Menu como abstrata e não interface pois Menu contém comportamento concreto
// se fosse interface não poderia ter campos nem construtores
public abstract class Menu {

    protected String name;

    public Menu(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract String getHTML(); // sobrescrita em: Link e Submenu

    public abstract void showOptions(); // sobrescrita em: Link e Submenu

    /*
    Menu(COMPONENT) declara add e remove com UnsupportedOperationException
        Submenu(COMPOSITVE) sobrescreve add e remove, funciona
            Link(LEAF) não sobrescreve portanto herda a exceção
     */
    public void add(Menu menu) {
        throw new UnsupportedOperationException();
    }

    public void remove(Menu menu) {
        throw new UnsupportedOperationException();
    }
}
