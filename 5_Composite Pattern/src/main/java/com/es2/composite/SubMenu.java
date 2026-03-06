package com.es2.composite;

import java.util.ArrayList;
import java.util.List;

public class SubMenu extends Menu {

    private List<Menu> items;

    public SubMenu() {
        super(""); // chama construtor da classe pai
        this.items = new ArrayList<>(); // inicializa a lista de filhos
    }

    public void setLabel(String label) {
        this.name = label;
    }

    public SubMenu(String name) {
        super(name);
        this.items = new ArrayList<>();
    }

    @Override
    public void add(Menu menu) {
        items.add(menu);
    }

    public void addChild(Menu menu) {
        items.add(menu);
    }

    @Override
    public void remove(Menu menu) {
        items.remove(menu);
    }

    @Override
    public void showOptions() {
        System.out.println(name);
        for (Menu item : items) {
            item.showOptions();
        }
    }

    @Override
    public String getHTML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<ul>\n");
        for (Menu item : items) {
            sb.append("  <li>").append(item.getHTML()).append("</li>\n");
        }
        sb.append("</ul>");
        return sb.toString();
    }
}
