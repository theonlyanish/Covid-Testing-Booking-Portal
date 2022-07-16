package application.Services;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public abstract class Service {

    protected static final String myApiKey = "MCgRpFpPrmG8b8pMpzhRPTMbRdkMLF";
    protected static final String rootUrl = "https://fit3077.com/api/v1";
    protected static HttpClient client;
    protected static HttpRequest request;
    protected static String usersUrl = rootUrl + "/user";;
    protected static HttpResponse<String> response;
    protected static ObjectMapper objectMapper = new ObjectMapper();

    public static synchronized Service getInstance(){
        return null;
    }

}
