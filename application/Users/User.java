package application.Users;

import java.util.Date;
import java.util.Hashtable;
import java.util.Random;
import java.util.Scanner;

import application.Bookings.Booking;
import application.ServiceClient;
import application.TestTypes.PCR;
import application.TestTypes.RAT;
import application.TestTypes.TestType;
import application.Tests.Covid_Test;

// Following are declarations for User attributes
public abstract class User {

    private String id;
    private String givenName;
    private String familyName;
    private String userName;
    private String phoneNumber;
    protected Booking[] bookings;
    private Covid_Test[] testsTaken;
    private Covid_Test[] testsAdministered;
    private Hashtable<String, String> additionalInfo;

    private static final Scanner scanner = new Scanner(System.in);
    private final Random r = new Random();

    public User(String userId, String givenName, String familyName, String userName, String phoneNumber, Booking[] bookings, Covid_Test[] testsTaken, Covid_Test[] testsAdministered, Hashtable<String, String> additionalInfo) {
        this.id = userId;
        this.givenName = givenName;
        this.familyName = familyName;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.bookings = bookings;
        this.testsTaken = testsTaken;
        this.testsAdministered = testsAdministered;
        this.additionalInfo = additionalInfo;
    }
    // Following are getters and setter for all declared attributes
    public User() {
    }

    public String getId() {
        return id;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBookings(Booking[] bookings) {
        this.bookings = bookings;
    }

    public Covid_Test[] getTestsTaken() {
        return testsTaken;
    }

    public void setTestsTaken(Covid_Test[] testsTaken) {
        this.testsTaken = testsTaken;
    }

    public Covid_Test[] getTestsAdministered() {
        return testsAdministered;
    }

    public void setTestsAdministered(Covid_Test[] testsAdministered) {
        this.testsAdministered = testsAdministered;
    }

    public Hashtable<String, String> getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(Hashtable<String, String> additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Booking[] getBookings() {
        // this function returns booking class for the specific user
        return bookings;
    }

    protected void addBooking(Booking newBooking) {
        // This function creates a booking for this user
        // Input - booking fields from booking class
        // Output - booking added to user
        Booking[] newBookings = new Booking[this.bookings.length+1];
        for (int i = 0; i < this.bookings.length; i ++) {
            newBookings[i] = this.bookings[i];
        }
        newBookings[newBookings.length-1] = newBooking;
        this.bookings = newBookings;
    }

    public void showMenu() {
        // This function will be called when the user is a healthcare worker
        // Input - int (choice)
        // Output - menu choice
        System.out.println("--------------------------------------------");
        int choice = -1;
        while (choice < 1 || choice > 4) {
            System.out.println("1. Book On-site Test for a Patient");
            System.out.println("2. Check Patient Booking Status");
            System.out.println("3. Conduct On-site Test");
            System.out.println("4. Issue RAT for Home Testing");
            System.out.println("5. Quit Application");
            System.out.print("Select an Option: ");
            choice = scanner.nextInt();

            if (choice == 1) {
                onSiteBooking();
                showMenu();
            } else if (choice == 2) {
                checkPatientStatus();
                showMenu();
            } else if (choice == 3) {
                conductOnSiteTest();
                showMenu();
            } else if (choice == 4) {
                issueRATTest();
                showMenu();
            } else if (choice == 5) {
                System.exit(0);
            } else {
                System.out.println("Invalid Input. Please select an option between 1 and 6.");
            }
        }
    }

    private void onSiteBooking() {
        Customer cust = new Customer().getPatientDetails();
        cust.displayTestingSites(true);
    }

    private void checkPatientStatus() {
        // This function is called to check the status
        // Input - user ID
        // Output - patient status
        User patient = null;

        while (patient == null) {
            System.out.print("Enter Username: ");
            String username = scanner.next();
            patient = ServiceClient.getUserByUsername(username);
            if (patient == null) {
                System.out.println("Couldnt find user. Try again.");
            }
        }
        patient.verifyBooking();
    }

    protected void verifyBooking(){
        // This function checks the length of booking class in user to verify booking
        // Input - booking in user
        // Output - booking verification
        if(bookings != null) {
            System.out.println("Booking status: Done");
//          System.out.println("Booking status: " + bookings[bookings.length-1].getStatus());
        } else {
            System.out.println("No booking was found, return to menu to book.");
        }
    }

    protected void conductOnSiteTest() {
        // This function is for updating status for conducting on site test
        // Input - patient id, qr code
        // Output - Status change
        User patient = null;
        Booking custBooking = null;
        System.out.print("Enter Patient Username: ");
        String patientUsername = scanner.next();



        patient = ServiceClient.getUserByUsername(patientUsername);
        while (patient == null) {
            System.out.println("Could not find patient. Try again");
            patient = ServiceClient.getUserByUsername(patientUsername);
        }

        System.out.print("Enter QR Code: ");
        String bookingId = scanner.next();

//        Booking[] bookings = ServiceClient.getAllBookings();
//        for (Booking b: bookings) {
//            if (b.getCustomer().getGivenName().equals(patient.givenName)) {
//                custBooking = b;
//            }
//        }

        if (patient.getBookings() == null) {
            System.out.println("Test done. Status updated.");
        } else {
            custBooking = ServiceClient.getBookingById(bookingId);
            while (custBooking == null) {
                System.out.println("Could not find booking. Try again");
                System.out.print("Enter QR Code: ");
                bookingId = scanner.next();
                ServiceClient.getBookingById(bookingId);
            }
        }

            TestType testType = symptomsInterview();

            Covid_Test test = new Covid_Test(String.valueOf(r.nextInt()), testType, (Customer) patient, this,
                    custBooking, testType.getTestResult(), "Test Completed", "", new Date().toString(),
                    testType.getDateOfResults().toString(), new Date(), new Date(),
                    new Hashtable<String, String> ());

            custBooking.setCovidTests(new Covid_Test[]{test});
            custBooking.setStatus(test.getStatus());
        }

    //This is a factory method
    protected TestType symptomsInterview() {
        // This function acts as the menu for symptom interview
        // Input - int (choice)
        // Output - returns test type to be taken
        int choice = -1;
        while (choice < 1 || choice > 2) {
            System.out.println("Describe the patients symptoms:");
            System.out.println("1) No symptoms");
            System.out.println("2) Severe symptoms");
            choice = scanner.nextInt();

            if (choice == 1) {
                return new PCR();
            } else if (choice == 2) {
                return new RAT();
            } else {
                System.out.println("Invalid Input. Please select an option between 1 and 2.");
            }
        }
        return new RAT();
    }

    protected void issueRATTest() {
        if (bookings == null) {
            System.out.println("Cannot find booking. Please register first before collecting RAT Kit");
        } else{
            Booking custBooking = bookings[bookings.length-1];
            custBooking.getAdditionalInfo().put("KitPresent","Yes");
            System.out.println("Email has been sent to user with URL to connect for testing.");
        }

    }
}