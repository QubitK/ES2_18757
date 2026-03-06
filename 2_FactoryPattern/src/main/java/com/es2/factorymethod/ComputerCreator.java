package com.es2.factorymethod;

public class ComputerCreator extends Creator {

    @Override
    public Product createProduct() {
        return new Computer();
    }

}
