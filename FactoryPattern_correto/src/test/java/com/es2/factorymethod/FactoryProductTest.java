package com.es2.factorymethod;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FactoryProductTest {

    @Test
    void testComputerCreatorReturnsComputer() {
        Creator creator = new ComputerCreator();
        Product p = creator.createProduct();
        assertNotNull(p);
        assertTrue(p instanceof Computer);
    }

    @Test
    void testSoftwareCreatorReturnsSoftware() {
        Creator creator = new SoftwareCreator();
        Product p = creator.createProduct();
        assertNotNull(p);
        assertTrue(p instanceof Software);
    }

    @Test
    void testComputerCreatorIsCreator() {
        assertTrue(new ComputerCreator() instanceof Creator);
    }

    @Test
    void testSoftwareCreatorIsCreator() {
        assertTrue(new SoftwareCreator() instanceof Creator);
    }

}
