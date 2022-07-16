package application;

import application.Services.UserService;
import application.Users.User;

import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    private static User currentUser;

    public static void main(String args[]) throws Exception {
        System.out.println("Welcome to the COVID-19 Test Booking System");
        System.out.println("--------------------------------------------");
        logIn();
        System.out.println("Welcome " + currentUser.getGivenName() + " " + currentUser.getFamilyName());
        System.out.println("--------------------------------------------");
        currentUser.showMenu();
    }

    private static void logIn() {
        // This function acts as the first step of the portal, the user is asked to sign in
        // Input - string (username), string (password)
        // Output - login confirmation or error msg
        String username = "";
        String password = "";
        Boolean loggedIn = false;
        System.out.println("Log In");
        while (loggedIn == false) {
            System.out.print("Enter username: ");
            username = scanner.next();
            System.out.print("Enter password: ");
            password = scanner.next();
            try {
                loggedIn = ServiceClient.userLogIn(username, password);
            } catch (Exception e) {
                System.out.println(e);
            }
            if (!loggedIn) {
                System.out.println("Invalid Log In. Please try again.");
            }
        }
        System.out.println("Successfully logged in!");

        currentUser = ServiceClient.getUserByUsername(username);
        if (currentUser == null){
            logIn();
        }
    }
}