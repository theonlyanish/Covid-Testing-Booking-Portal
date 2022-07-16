package application.Users;

import java.util.Hashtable;
import java.util.Scanner;

import application.Bookings.Booking;
import application.Tests.Covid_Test;


public class HealthcareWorker extends User {

    private static Scanner scanner = new Scanner(System.in);

    public HealthcareWorker(String id, String givenName, String familyName, String userName, String phoneNumber, Booking[] bookings, Covid_Test[] testsTaken, Covid_Test[] testsAdministered, Hashtable<String, String> additionalInfo) {
        super(id, givenName, familyName, userName, phoneNumber, bookings, testsTaken, testsAdministered, additionalInfo);
    }

    public HealthcareWorker() {
        super();
    }

    @Override
    public void showMenu() {
        super.showMenu();
    }
}