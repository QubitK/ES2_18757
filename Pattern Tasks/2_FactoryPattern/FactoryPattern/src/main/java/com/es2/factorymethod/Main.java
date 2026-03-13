package com.es2.factorymethod;

public class Main {

    static void main() {
        // Main só conhece Creator — não sabe que existe ComputerCreator
        // O objeto real em memória é ComputerCreator, mas a referência é Creator (polimorfismo)
        Creator computerCreator = new ComputerCreator(); // cria referência a Creator para ComputerCreator

        // createProduct() é chamado no objeto real (ComputerCreator), que devolve new Computer()
        // Main não sabe que é um Computer — só vê Product (abstração)
        Product myComputer = computerCreator.createProduct(); // acedemos a createProduct() via polimorfismo do metodo abstrato em Creator

        // Main usa o produto através da interface Product, sem conhecer Computer diretamente
        myComputer.setBrand("Legion"); // acedemos ao setter via polimorfismo dos metodos da interface Product para Coputer ou Software

        // Mesmo padrão repetido — Main trata SoftwareCreator exatamente igual a ComputerCreator
        Creator softwareCreator = new SoftwareCreator();
        Product mySoftware = softwareCreator.createProduct();
        mySoftware.setBrand("Microsoft"); // acedemos ao setter via polimorfismo dos metodos da interface Product para Coputer ou Software

        // toString() de cada objeto concreto é chamado aqui (Computer e Software)
        // Main continua sem os conhecer diretamente
        System.out.println(myComputer);
        System.out.println(mySoftware);
    }

}

/*
  "extends" directly gives Inheritance

  Abstraction comes from Creator being abstract:
  - Creator defines what must exist (createProduct()) without saying how
  - It's a contract — any subclass must implement it
  - This hides the creation details from the client (Main)

  Polymorphism comes from using the parent type as the reference:
  - Because ComputerCreator extends Creator, you can write Creator computerCreator = new ComputerCreator()
  - The same call computerCreator.createProduct() behaves differently depending on the real object
  - Without extends, this substitution would be impossible
 */