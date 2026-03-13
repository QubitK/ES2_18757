package com.es2.compositepattern;

// COMPOSITE PATTERN

public class Main {
    public static void main(String[] args) {
        SubMenu root = new SubMenu("Main Menu");

        SubMenu produtos = new SubMenu("Produtos");
        root.addChild(produtos);
        produtos.addChild(new Link("Laptops", "loja.com/laptops"));
        produtos.addChild(new Link("Smartphones", "loja.com/smartphones"));

        SubMenu empresa = new SubMenu("Empresa");
        root.addChild(empresa);
        empresa.addChild(new Link("Sobre nós", "empresa.com/sobre"));
        empresa.addChild(new Link("Contacto", "empresa.com/contacto"));

        root.addChild(new Link("Início", "index.html"));

        System.out.println("<ol>");
        root.showOptions("   ");
        System.out.println("</ol>");
    }
}
