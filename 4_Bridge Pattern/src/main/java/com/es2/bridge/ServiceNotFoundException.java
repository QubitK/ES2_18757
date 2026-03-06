package com.es2.bridge;

public class ServiceNotFoundException extends Exception {

    public ServiceNotFoundException(String serviceId) {
        super("Service not found: " + serviceId);
    }
}
