package com.es2.factorymethod;

public class Main{
    static void main(){
        try {
            // Instanciar objetos computer e software recorrendo a FactoryPattern(FactoryProduct) - nao usar 'new' diretamente
            Product myComputer = FactoryProduct.makeProduct("computer");
            myComputer.setBrand("Legion");
            Product mySoftware = FactoryProduct.makeProduct("software");
            mySoftware.setBrand("Microsoft");

            // println chama diretamente os métodos "toString()" de Computer ou Software
            System.out.println(myComputer);
            System.out.println(mySoftware);
        } catch (UndefinedProductException e) {
            System.out.println("Erro: Tipo de produto não reconhecido.");
        }
    }
}
