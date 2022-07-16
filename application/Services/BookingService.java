package application.Services;

import application.Bookings.Booking;
import application.ServiceClient;
import application.Users.User;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class BookingService extends Service {

    private static BookingService instance;

    private BookingService() {}

    public static synchronized BookingService getInstance(){
        if(instance == null){
            instance = new BookingService();
        }
        return instance;
    }

    public Booking getBookingById(String bookingId) throws Exception{
        Booking currentBooking;
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);

        request = HttpRequest
                .newBuilder(URI.create(rootUrl + "/booking/" + bookingId))
                .setHeader("Authorization", myApiKey)
                .GET()
                .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectNode jsonNode = objectMapper.readValue(response.body(), ObjectNode.class);

        currentBooking = objectMapper.treeToValue(jsonNode, Booking.class);
        return currentBooking;
    }

    public void createBooking(Booking booking) throws Exception {

        User cust = ServiceClient.getUserByUsername(booking.getCustomer().getUserName());

        String jsonString = "{\"customerId\":\"" + cust.getId() + "\"," +
                "\"testingSiteId\":\"" + booking.getTestingSite().getId() + "\"," +
                "\"startTime\":\"2022-04-29T08:16:04.178Z\"}";


        client = HttpClient.newHttpClient();
        request = HttpRequest.newBuilder(URI.create(rootUrl + "/booking"))
                .setHeader("Authorization", myApiKey)
                .header("Content-Type","application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 201) {
            System.out.println(response.statusCode());
            throw new Exception();
        }

    }

    public void updateBooking(Booking booking) throws Exception {
        String jsonString = "{\"status\":\"" + booking.getStatus() + "\"}";

        client = HttpClient.newHttpClient();
        request = HttpRequest.newBuilder(URI.create(rootUrl + "/booking/" + booking.getId()))
                .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonString))
                .setHeader("Authorization", myApiKey)
                .header("Content-Type","application/json")
                .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.out.println(response.statusCode());
            throw new Exception();
        }
    }

    public Booking[] getAllBookings() throws Exception {

        ArrayList<Booking> bookings = new ArrayList<>();

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);

        request = HttpRequest
                .newBuilder(URI.create(rootUrl + "/booking"))
                .setHeader("Authorization", myApiKey)
                .GET()
                .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectNode[] jsonNodes = objectMapper.readValue(response.body(), ObjectNode[].class);

        for (ObjectNode node: jsonNodes) {
            Booking currentBooking = objectMapper.treeToValue(node, Booking.class);
            bookings.add(currentBooking);
        }
        return (Booking[]) bookings.toArray();
    }
}
