package application.Services;

import application.Users.Customer;
import application.Users.HealthcareWorker;
import application.Users.Receptionist;
import application.Users.User;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UserService extends Service {

    private static UserService instance;

    private UserService() {}

    public static synchronized UserService getInstance(){
        if(instance == null){
            instance = new UserService();
        }
        return instance;
    }

    public boolean userLogIn(String username, String password) throws Exception {
        client = HttpClient.newHttpClient();
        String jsonString = "{" +
                "\"userName\":\"" + username + "\"," +
                "\"password\":\"" + password + "\"" +
                "}";

        String usersLoginUrl = usersUrl + "/login";
        //client = HttpClient.newHttpClient();
        request = HttpRequest.newBuilder(URI.create(usersLoginUrl + "?jwt=false"))
                .setHeader("Authorization", myApiKey)
                .header("Content-Type","application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return true;
        }
        return false;
    }

    public User getUserById(String id) throws Exception {
        User currentUser = null;
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);

        request = HttpRequest
                .newBuilder(URI.create(usersUrl + "/" + id))
                .setHeader("Authorization", myApiKey)
                .GET()
                .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectNode jsonNode = objectMapper.readValue(response.body(), ObjectNode.class);

        currentUser = objectMapper.treeToValue(jsonNode, Customer.class);
        return currentUser;
    }

    public User getUserByUsername(String username) throws Exception {
        User currentUser = null;
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);

        request = HttpRequest
                .newBuilder(URI.create(usersUrl))
                .setHeader("Authorization", myApiKey)
                .GET()
                .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectNode[] jsonNodes = objectMapper.readValue(response.body(), ObjectNode[].class);

        for (ObjectNode node: jsonNodes) {
            if (node.get("userName").asText().equals(username)) {
                if (node.get("isCustomer").asBoolean()) {
                    currentUser = objectMapper.treeToValue(node, Customer.class);
                } else if (node.get("isReceptionist").asBoolean()) {
                    currentUser = objectMapper.treeToValue(node, Receptionist.class);
                } else {
                    currentUser = objectMapper.treeToValue(node, HealthcareWorker.class);
                }
                break;
            }
        }
        return currentUser;
    }

    public void createUser(User user) throws Exception {

        String jsonString = "{\"givenName\":\"" + user.getGivenName() + "\"," +
                            "\"familyName\":\"" + user.getFamilyName() + "\"," +
                            "\"userName\":\"" + user.getUserName() + "\"," +
                            "\"password\":\""+ user.getUserName() + "\"," +
                            "\"phoneNumber\":\"" + user.getPhoneNumber() + "\"," +
                            "\"isCustomer\":true," +
                            "\"isReceptionist\":false," +
                            "\"isHealthcareWorker\":false}";

        client = HttpClient.newHttpClient();
        request = HttpRequest.newBuilder(URI.create(rootUrl + "/user"))
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

}
