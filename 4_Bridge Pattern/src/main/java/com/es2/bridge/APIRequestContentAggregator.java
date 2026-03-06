package com.es2.bridge;

/**
 * Refined Abstraction in the Bridge Pattern.
 * Aggregates all contents of a service into a single concatenated result.
 */
public class APIRequestContentAggregator extends APIRequest {

    /**
     * Aggregates all content stored in the specified service,
     * preserving insertion order, ignoring the contentId parameter.
     *
     * @param serviceId the identifier of the target service
     * @param contentId not used; aggregation always covers all stored content
     * @return the concatenation of all stored values in insertion order
     * @throws ServiceNotFoundException if the service does not exist
     */
    @Override
    public String getContent(String serviceId, String contentId) throws ServiceNotFoundException {
        APIServiceInterface service = services.get(serviceId);
        if (service == null) {
            throw new ServiceNotFoundException(serviceId);
        }
        StringBuilder sb = new StringBuilder();
        for (String value : service.getAll().values()) {
            sb.append(value);
        }
        return sb.toString();
    }
}
