package com.es2.factorymethod;

public class Main {

    static void main() {
        Creator computerCreator = new ComputerCreator();
        Product myComputer = computerCreator.createProduct();
        myComputer.setBrand("Legion");

        Creator softwareCreator = new SoftwareCreator();
        Product mySoftware = softwareCreator.createProduct();
        mySoftware.setBrand("Microsoft");

        System.out.println(myComputer);
        System.out.println(mySoftware);
    }

}
