package com.es2.bridgepattern;

public class APIRequestContentAggregator extends APIRequest {

    public APIRequestContentAggregator(){
        super();
    };

    @Override
    public String getContent(String serviceId, String contentId) throws ServiceNotFoundException {
        return super.getContent(serviceId, "0");
    }
}
