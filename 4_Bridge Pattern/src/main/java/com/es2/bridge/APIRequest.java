package com.es2.bridge;

import java.util.LinkedHashMap;

/**
 * Abstraction in the Bridge Pattern.
 * The client interacts with this class to access service implementations
 * that are instantiated dynamically at runtime.
 * Delegates all content operations to the APIServiceInterface implementor.
 */
public class APIRequest {

    protected LinkedHashMap<String, APIServiceInterface> services = new LinkedHashMap<>();
    private int serviceCounter = 0;

    /**
     * Registers a service implementation and returns its identifier.
     *
     * @param service the service implementor to register
     * @return the identifier assigned to this service
     */
    public String addService(APIServiceInterface service) {
        String id = String.valueOf(serviceCounter++);
        services.put(id, service);
        return id;
    }

    /**
     * Stores content in the specified service.
     *
     * @param serviceId the identifier of the target service
     * @param content   the content to store
     * @return the identifier assigned to the stored content
     * @throws ServiceNotFoundException if the service does not exist
     */
    public String setContent(String serviceId, String content) throws ServiceNotFoundException {
        APIServiceInterface service = services.get(serviceId);
        if (service == null) {
            throw new ServiceNotFoundException(serviceId);
        }
        return service.store(content);
    }

    /**
     * Retrieves content from the specified service.
     *
     * @param serviceId the identifier of the target service
     * @param contentId the identifier of the content to retrieve
     * @return the stored value, or null if the content does not exist
     * @throws ServiceNotFoundException if the service does not exist
     */
    public String getContent(String serviceId, String contentId) throws ServiceNotFoundException {
        APIServiceInterface service = services.get(serviceId);
        if (service == null) {
            throw new ServiceNotFoundException(serviceId);
        }
        return service.get(contentId);
    }
}
