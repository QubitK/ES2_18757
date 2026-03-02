package com.es2.factorymethod;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FactoryProductTest {

    @Test
    void testCreateComputer() throws UndefinedProductException {
        Product p = FactoryProduct.makeProduct("computer");
        assertNotNull(p);
        assertTrue(p instanceof Computer);
    }

    @Test
    void testCreateSoftware() throws UndefinedProductException {
        Product p = FactoryProduct.makeProduct("software");
        assertNotNull(p);
        assertTrue(p instanceof Software);
    }

    @Test
    void testUndefinedProduct() {
        assertThrows(UndefinedProductException.class, () -> {
            FactoryProduct.makeProduct("unknown");
        });
    }
}