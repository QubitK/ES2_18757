package com.es2.factorymethod;

public class SoftwareCreator extends Creator {

    @Override
    public Product createProduct() {
        return new Software();
    }

}
