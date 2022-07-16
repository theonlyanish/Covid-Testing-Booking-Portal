package application.Services;

import application.Bookings.Booking;
import application.TestingSites.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TestingSiteService extends Service {

    private static TestingSiteService instance;

    private TestingSiteService() {}

    public static synchronized TestingSiteService getInstance(){
        if(instance == null){
            instance = new TestingSiteService();
        }
        return instance;
    }

    public TestingSite[] getAllTestingSites() throws Exception {
        request = HttpRequest
                .newBuilder(URI.create(rootUrl + "/testing-site"))
                .setHeader("Authorization", myApiKey)
                .GET()
                .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        TestingSite[] testingSites = new TestingSite[response.body().length()];

        ObjectNode[] jsonNodes = new ObjectMapper().readValue(response.body(), ObjectNode[].class);
        FacilityType facilityType = FacilityType.WALKIN; //default value
        for (int i = 0; i < jsonNodes.length; i ++) {
            ObjectNode node = jsonNodes[i];

            if (node.get("additionalInfo").get("facilityType") != null &&  node.get("additionalInfo").get("siteType") != null) {
                if (node.get("additionalInfo").get("facilityType").textValue().equals("DriveThrough")) {
                    facilityType = FacilityType.DRIVETHROUGH;
                }
                if (node.get("additionalInfo").get("siteType").asText() == "Hospital") {
                    testingSites[i] = objectMapper.treeToValue(node, Hospital.class);
                } else if (node.get("additionalInfo").get("siteType").asText() == "Clinic") {
                    testingSites[i] = objectMapper.treeToValue(node, Clinic.class);
                } else if (node.get("additionalInfo").get("siteType").asText().equals("GP")) {
                    testingSites[i] = objectMapper.treeToValue(node, GP.class);
                } else {
                    testingSites[i] = objectMapper.treeToValue(node, UserHome.class);
                }
            } else {
                testingSites[i] = objectMapper.treeToValue(node, UserHome.class);
            }
            testingSites[i].setFacilityType(facilityType);
        }
        return testingSites;
    }

    public void createTestingSite(TestingSite testingSite) throws Exception {
        String addressJsonString = "{\"latitude\":" + testingSite.getAddress().getLatitude() + "," +
                "\"longitude\":" + testingSite.getAddress().getLongitude() + "," +
                "\"street\":\"" + testingSite.getAddress().getStreet() + "\"," +
                "\"suburb\":\"" + testingSite.getAddress().getSuburb() + "\"," +
                "\"state\":\"" + testingSite.getAddress().getState() + "\"," +
                "\"postcode\":\"" + testingSite.getAddress().getPostcode() + "\"}";

        String jsonString = "{\"name\":\"" + testingSite.getName() + "\"," +
                "\"address\":" + addressJsonString + "}";

        client = HttpClient.newHttpClient();
        request = HttpRequest.newBuilder(URI.create(rootUrl + "/testing-site"))
                .setHeader("Authorization", myApiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 201) {
            System.out.println(response.statusCode());
            throw new Exception();
        }
    }

}
