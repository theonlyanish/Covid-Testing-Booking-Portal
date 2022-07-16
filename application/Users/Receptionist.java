package application.Users;

import java.util.Hashtable;
import java.util.Scanner;

import application.Bookings.Booking;
import application.Tests.Covid_Test;


public class Receptionist extends User {

    private static Scanner scanner = new Scanner(System.in);

    public Receptionist(String userId, String givenName, String familyName, String userName, String phoneNumber, Booking[] bookings, Covid_Test[] testsTaken, Covid_Test[] testsAdministered, Hashtable<String, String> additionalInfo) {
        super(userId, givenName, familyName, userName, phoneNumber, bookings, testsTaken, testsAdministered, additionalInfo);
    }

    public Receptionist() {
        super();
    }

    @Override
    public void showMenu() {
        super.showMenu();
    }
}