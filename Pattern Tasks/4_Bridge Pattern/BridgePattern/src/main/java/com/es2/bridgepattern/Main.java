package com.es2.bridgepattern;

public class Main {
    public static void main(String[] args) {
        try {
            APIRequest request = new APIRequest(); // creating request instance
            String moodleServiceId = request.addService(new APIMoodle()); // creating moodle service instance, adding it service to request and its service id

            String subject1 = request.setContent(moodleServiceId, "Subject 1"); // added to APIMoodle.content via APIRequest.services
            String subject2 = request.setContent(moodleServiceId, "Subject 2"); // added to APIMoodle.subject via APIRequest.services

            System.out.println("\n" + request.getContent(moodleServiceId, subject1)); // Prints sub1 directly

            String content_sub2 = request.getContent(moodleServiceId, subject2);
            System.out.println("\n" + content_sub2); // sub 2 indirectly printed to test getContent

            System.out.println("\n" + request.getContent(moodleServiceId, "0")); // Printing all

            // Alternative APIRequest class that always aggregates data by forcing contentId = 0
            APIRequest requestAggregator = new APIRequestContentAggregator();
            String moodleServiceId2 = requestAggregator.addService(new APIMoodle());
            requestAggregator.setContent(moodleServiceId2, "SUBBBB WAHT");
            System.out.println("\n" + requestAggregator.getContent(moodleServiceId2, "0"));

        } catch (ServiceNotFoundException e) {
             e.printStackTrace();
        }
    }
}
