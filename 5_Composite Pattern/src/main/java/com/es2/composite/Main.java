package com.es2.composite;

public class Main {
    public static void main(String[] args) {
        SubMenu mainMenu = new SubMenu("Main Menu");

        SubMenu products = new SubMenu("Products");
        products.add(new Link("Product A", "products/a.html"));
        products.add(new Link("Product B", "products/b.html"));

        SubMenu support = new SubMenu("Support");
        support.add(new Link("FAQ", "support/faq.html"));
        support.add(new Link("Contact", "support/contact.html"));

        SubMenu services = new SubMenu("Services");
        services.add(new Link("Consulting", "services/consulting.html"));
        services.add(support);

        mainMenu.add(new Link("Home", "index.html"));
        mainMenu.add(products);
        mainMenu.add(services);
        mainMenu.add(new Link("About", "about.html"));

        System.out.println(mainMenu.getHTML());
    }
}
