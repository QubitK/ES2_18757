package com.es2.bridgepattern;

import java.security.Provider;
import java.util.HashMap;

public class APIRequest {
    protected HashMap<String, APIServiceInterface> services;

    APIRequest(){
        this.services = new HashMap<>();
    }

    public String addService(APIServiceInterface service){
        String servicesId = String.valueOf(services.size() + 1);
        this.services.put(servicesId, service);
        return servicesId;
    }

    public String getContent(String serviceId, String contentId) throws ServiceNotFoundException{
        APIServiceInterface service = services.getOrDefault(serviceId, null);
        if(service == null) throw new ServiceNotFoundException();
        return service.getContent(contentId);
    }

    public String setContent(String serviceId, String content) throws ServiceNotFoundException{
        APIServiceInterface service = services.getOrDefault(serviceId, null);
        if(service == null) throw new ServiceNotFoundException();
        return service.setContent(content);
    }
}
