package com.es2.loggingproject.M7_decorator_monitoring;

import com.es2.loggingproject.M3_bridge_destination.LogDestinationInterface;
import com.es2.loggingproject.M4_composite_category.LogComponent;

public class CategoryDecorator extends LogDecorator {

    private final String categoryName;

    public CategoryDecorator(LogComponent wrapped, String categoryName){
        super(wrapped);
        this.categoryName = categoryName;
    }

    @Override
    public void outputTo(LogDestinationInterface destination) {
        // Apenas delega, a category fica disponível através de getCategoryName()
        super.outputTo(destination);
    }

    @Override
    public String getCategoryName() {
        return categoryName;
    }

}
