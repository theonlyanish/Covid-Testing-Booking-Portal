package application;

import application.Bookings.Booking;
import application.Services.BookingService;
import application.Services.TestingSiteService;
import application.Services.UserService;
import application.TestingSites.TestingSite;
import application.Users.User;

public class ServiceClient {

    private static UserService userService = UserService.getInstance();
    private static BookingService bookingService = BookingService.getInstance();
    private static TestingSiteService testingSiteService = TestingSiteService.getInstance();

    public static Booking getBookingById(String bookingId) {
        try {
            return bookingService.getBookingById(bookingId);
        } catch (Exception e) {
            System.out.println("Booking Not Found. Try Again.");
        }
        return null;
    }

    public static TestingSite[] getAllTestingSites(){
        try {
            return testingSiteService.getAllTestingSites();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Error getting testing sites");
        }
        return null;
    }

    public static boolean userLogIn(String username, String password) {
        try {
            return userService.userLogIn(username, password);
        } catch (Exception e) {
            System.out.println("Unable to Log in. Try again.");
        }
        return false;
    }

    public static User getUserById(String id) {
        try {
            return userService.getUserById(id);
        } catch (Exception e) {
            System.out.println("Patient Not Found. Try Again.");
        }
        return null;
    }

    public static User getUserByUsername(String username) {
        try {
            return userService.getUserByUsername(username);
        } catch (Exception e){
            System.out.println("Unable to find user. Try again.");
        }
        return null;
    }

    public static void createUser(User user) {
        try {
            userService.createUser(user);
        } catch (Exception e){
            System.out.println("Unable to create user. Try again.");
        }
    }

    public static void createBooking(Booking booking) {
        try {
            bookingService.createBooking(booking);
        } catch (Exception e){
            System.out.println(e);
            System.out.println("Unable to create booking. Try again.");
        }
    }

    public static void updateBooking(Booking booking) {
        try {
            bookingService.updateBooking(booking);
        } catch (Exception e){
            System.out.println("Unable to update booking. Try again.");
        }
    }

    public static void createTestingSite(TestingSite testingSite) {
        try {
            testingSiteService.createTestingSite(testingSite);
        } catch (Exception e){
            System.out.println("Unable to create Testing Site. Try again.");
        }
    }

    public static Booking[] getAllBookings() {
        Booking[] bookings = null;
        try {
            bookings = bookingService.getAllBookings();
        } catch (Exception e){
            System.out.println("Unable to get Bookings. Try again.");
        }
        return bookings;
    }
}