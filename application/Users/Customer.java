package application.Users;

import java.util.*;

import application.Bookings.Booking;
import application.ServiceClient;
import application.Services.TestingSiteService;
import application.Tests.Covid_Test;
import application.TestingSites.*;

public class Customer extends User {

    private static Scanner scanner = new Scanner(System.in);
    private Random r = new Random();
    private TestingSite[] testingSites;
    private Customer state = this;

    public Customer(String userId, String givenName, String familyName, String userName, String phoneNumber, Booking[] bookings, Covid_Test[] testsTaken, Covid_Test[] testsAdministered, Hashtable<String, String> additionalInfo) {
        super(userId, givenName, familyName, userName, phoneNumber, bookings, testsTaken, testsAdministered, additionalInfo);
        testingSites = getAllTestingSites();
        setState(this);
    }

    public Customer() {
        super();
    }

    @Override
    public void showMenu() {
        // This function will display the menu for customer, this leads to other options based on number input
        // Input - int (choice)
        // Output - list of testing sites or sub menus for other options
        System.out.println("--------------------------------------------");
        int choice = -1;
        while (choice < 1 || choice > 6) {
            System.out.println("MENU");
            System.out.println("1. View All Testing Sites and Book Test");
            System.out.println("2. Search for Testing Sites and Book Test");
            System.out.println("3. Register for Home Testing");
            System.out.println("4. Administer Home Test");
            System.out.println("5. Check Booking Status/Verify Booking");
            System.out.println("6. Quit Application");
            System.out.print("Select an Option: ");
            choice = scanner.nextInt();

            if (choice == 1) {
                displayTestingSites(false);
                this.showMenu();
            } else if (choice == 2) {
                searchForTestingSites();
                this.showMenu();
            } else if (choice == 3) {
                registerforHomeTesting();
                this.showMenu();
            } else if (choice == 4) {
                administerHomeTest();
                this.showMenu();
            } else if (choice == 5) {
               verifyBooking();
                this.showMenu();
            } else if (choice == 6) {
                System.exit(0);
            } else {
                System.out.println("Invalid Input. Please select an option between 1 and 6.");
            }
        }
    }

    protected void displayTestingSites(Boolean onsite) {
        // This function will display the testing sites which is initiated when the user selects 1 in menu
        // selection and call two other functions based on input
        // Input - int (choice)
        // Output - testing sites chosen
        if (this.testingSites == null) {
            testingSites = getAllTestingSites();
        }
        System.out.println("--------------------------------------------");
        System.out.println("RESULTS: TESTING SITES");
        for (int i = 0; i < testingSites.length; i ++ ) {
            if (testingSites[i] != null) {
                System.out.println(i+1 + ") " + testingSites[i].toString());
            }
        }
        System.out.println("--------------------------------------------");
        int choice = -1;
        while (testingSites.length > 0 && choice < 1 || choice > testingSites.length - 1) {
            System.out.print("Select a Testing Site for Booking: ");
            choice = scanner.nextInt();

            if (choice > 0 && choice < testingSites.length - 1) {
                initiateBooking(testingSites[choice - 1], onsite);
            } else {
                System.out.println("Invalid Input. Please select a valid testing site.");
            }
        }
        setState(this);
    }

    private TestingSite[] getAllTestingSites() {
        try {
            testingSites = ServiceClient.getAllTestingSites();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Error getting testing sites");
            searchForTestingSites();
        }
        return testingSites;
    }

    private void searchForTestingSites() {
        if (this.testingSites == null) {
            testingSites = getAllTestingSites();
        }
        // This function acts as the menu when the second option of the main menu is selected
        // Input - int (choice)
        // Output - filtered testing site
        int choice = -1;
        while (choice < 1 || choice > 4) {

            System.out.println("1. Filter by Suburb Name");
            System.out.println("2. Filter by Testing Site Type");
            System.out.println("3. Filter by Facility Type (Drive-through/Walk-in)");
            System.out.println("4. Back to Previous Menu");
            System.out.print("Select an Option: ");
            choice = scanner.nextInt();

            if (choice == 1) {
                this.filterBySuburb();
                this.displayTestingSites(false);
            } else if (choice == 2) {
                this.filterByTestingSiteType();
                this.displayTestingSites(false);
            } else if (choice == 3) {
                this.filterByFacilityType();
                this.displayTestingSites(false);
            } else if (choice == 4) {
                this.showMenu();
            } else {
                System.out.println("Invalid Input. Please select an option between 1 and 4.");
            }
        }
    }

    private void filterBySuburb() {
        // this function filters the testing sites by suburbs
        // Input - string (suburb name)
        // Output - new list of sites sent to prev function
        System.out.print("Enter Suburb Name: ");
        String suburb = scanner.next();
        TestingSite[] testingSites = this.getAllTestingSites();
        //Arrays.stream(testingSites).filter(testingSite -> testingSite != null && testingSite.getAddress().getSuburb().equals(suburb)).toArray();

        for (int i = 0; i < testingSites.length; i++) {
            if (testingSites[i] == null || testingSites[i].getAddress().getSuburb().equals(suburb) == false) {
                testingSites[i] = null;
            }
        }
    }

    private void filterByFacilityType() {
        // this function filters the testing sites by facility types
        // Input - int (choice)
        // Output - new list of sites sent to parent function
        int choice = -1;
        FacilityType chosenFacilityType = FacilityType.WALKIN;
        while (choice < 1 || choice > 3) {
            System.out.println("Select Facility Type: ");
            System.out.println("1. Drive-through");
            System.out.println("2. Walk-in");
            System.out.println("3. Back to Previous Menu");
            System.out.print("Select an Option: ");
            choice = scanner.nextInt();

            if (choice == 1) {
                chosenFacilityType = FacilityType.DRIVETHROUGH;
            } else if (choice == 2) {
                chosenFacilityType = FacilityType.WALKIN;
            } else if (choice == 3) {
                this.searchForTestingSites();
            } else {
                System.out.println("Invalid Input. Please select an option between 1 and 3.");
            }
            TestingSite[] testingSites = this.getAllTestingSites();
            FacilityType finalChosenFacilityType = chosenFacilityType; // final temp variable
//            Arrays.stream(testingSites).filter(
//                    testingSite -> testingSite != null && testingSite.getFacilityType() == finalChosenFacilityType).toArray();

            for (int i = 0; i < testingSites.length; i++) {
                if (testingSites[i] == null || testingSites[i].getFacilityType().equals(finalChosenFacilityType) == false) {
                    testingSites[i] = null;
                }
            }

        }
    }

    private void filterByTestingSiteType() {
        // This function filters the testing sites by facility type
        // Input - int (choice)
        // Output - new list of sites sent to parent function
        int choice = -1;
        FacilityType chosenFacilityType = FacilityType.WALKIN;
        while (choice < 1 || choice > 4) {
            System.out.println("Select Testing Site Type: ");
            System.out.println("1. Hospital");
            System.out.println("2. Clinic");
            System.out.println("3. GP");
            System.out.println("4. Back to Previous Menu");
            System.out.print("Select an Option: ");
            choice = scanner.nextInt();

            if (choice == 1) {
//                Arrays.stream(testingSites).filter(Hospital.class::isInstance).toArray();
                for (int i = 0; i < testingSites.length; i++) {
                    if (testingSites[i] == null || !(testingSites[i] instanceof Hospital)) {
                        testingSites[i] = null;
                    }
                }
            } else if (choice == 2) {
                for (int i = 0; i < testingSites.length; i++) {
                    if (testingSites[i] == null || !(testingSites[i] instanceof Clinic)) {
                        testingSites[i] = null;
                    }
                }
            } else if (choice == 3) {
                for (int i = 0; i < testingSites.length; i++) {
                    if (testingSites[i] == null || !(testingSites[i] instanceof GP)) {
                        testingSites[i] = null;
                    }
                }
            } else if (choice == 4) {
                this.searchForTestingSites();
            } else {
                System.out.println("Invalid Input. Please select an option between 1 and 4.");
            }






        }
    }
    private void registerforHomeTesting(){
        // This function allows the user to register for home testing when the 3rd option is seleced
        // this will call a booking function where the user will enter details and select kit choice
        // Input - int (choice)
        // Output - string (confirmation)
        System.out.println("--------------------------------------------");
        Address myAddress = new Address(80, 123, "Monash", "", "Clayton", "VIC", "1234", new Hashtable<String, String>(){});
        UserHome userHome = new UserHome("home1", "home", "home description", "", "123456789", myAddress, FacilityType.WALKIN);
        ServiceClient.createTestingSite(userHome);
        userHome.setId(userHome.getHomeId());
        initiateBooking(userHome, false);

        Booking custBooking = this.getBookings()[this.getBookings().length-1];

        for (Booking b : this.getBookings()) {

        }

        int choice = -1;
        while (choice < 1 || choice > 2) {
            System.out.println("1. Visit site to receive kit");
            System.out.println("2. Already have kit?");
            System.out.println("Select an Option: ");
            choice = scanner.nextInt();

            if (choice == 1) {
                custBooking.getAdditionalInfo().put("KitPresent","No");
                custBooking.setQrCode(custBooking.generateQRCode());
                custBooking.getAdditionalInfo().put("Url", custBooking.generateURL());
                System.out.println("Email has been sent to user with QR Code for collection and URL to connect for testing.");
            } else if (choice == 2){
                custBooking.getAdditionalInfo().put("KitPresent","Yes");
                System.out.println("Email has been sent to user with URL to connect for testing.");
            }
        }
        //ServiceClient.updateBooking(custBooking);
        // staff will update that user has received kit
        setState(this);
    }

    private void administerHomeTest() {
        // This function will be used for administering the home test and is shown when the user selects the
        // 4th option
        // Input - String (Userhome)
        // Output - Booking status changed and confirmation msg returned
        if (this.getBookings() == null) {
            System.out.println("No booking exists.");
        } else {
            Booking custBooking = this.getBookings()[this.getBookings().length-1];
            custBooking.setStatus("Done");
            //ServiceClient.updateBooking(custBooking);
            System.out.println("Your status has been updated");
            setState(this);
        }
    }

    protected void initiateBooking(TestingSite testingSite, Boolean onsite) {
        // This function is initites the booking for either the user or for a new user
        // Input - int (choice)
        // Output - booking updates
        int choice = -1;
        while (choice < 1 || choice > 3) {
            if (onsite == false) {
                System.out.println("Who will be receiving the test?");
                System.out.println("1. You");
                System.out.println("2. Someone else");
                System.out.print("Select an Option: ");
                choice = scanner.nextInt();
            } else {
                choice = 1;
            }

            if (choice == 1) {
                addCustomerBooking(testingSite, this);
            } else if (choice == 2) {
                Customer patient = getPatientDetails();
                addCustomerBooking(testingSite, patient);
            } else {
                System.out.println("Invalid Input. Please select an option between 1 and 2.");
            }
        }
        setState(this);
    }

    protected Customer getPatientDetails() {
        // This function registers a new user
        // Input - String (data)
        // Output - summary of values entered
        System.out.println("Enter Patient Details:");
        System.out.print("Given Name: ");
        String givenName = scanner.next();
        System.out.print("Family Name: ");
        String familyName = scanner.next();
        System.out.print("UserName: ");
        String userName = scanner.next();
        System.out.print("Phone Number: ");
        String phoneNumber = scanner.next();

        Customer newCust = new Customer(String.valueOf(r.nextInt()), givenName, familyName, userName, phoneNumber, new Booking[]{},
                new Covid_Test[]{}, new Covid_Test[]{}, new Hashtable<String, String>());
        ServiceClient.createUser(newCust);
        return newCust;
    }

    private void addCustomerBooking(TestingSite testingSite, Customer customer) {
        // this function is for booking a test for the customer
        // Input - testing site and customer details
        // Output - confirmation msg, random pin generated, qr code generated
        if (this.getBookings() == null) {
            this.setBookings(new Booking[]{});
        }
        String bookingId = this.getId() + this.getBookings().length;
        String smsPin = generateSMSPin();
        Booking newBooking = new Booking(bookingId, customer, testingSite, new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis()), smsPin, "INITIATED", new Covid_Test[]{}, "",
                new Hashtable<String, String>());
        this.addBooking(newBooking);
        ServiceClient.createBooking(newBooking);
        System.out.println("Booking Initiated.");
        System.out.println("SMS PIN sent to user: " + smsPin);
        System.out.println("QR Code Generated.");
        setState(this);
    }

    private String generateSMSPin() {
        // Small function for generating random pin
        return String.valueOf(r.nextInt(999999)); //generate a random 6 digit pin
    }

    //Observer Subject Methods
    public Customer getState() {
        return state;
    }

    public void setState(Customer state) {
        this.state = state;
        notifyAllObservers();
    }

    public void notifyAllObservers(){
        for (Booking booking : bookings) {
            booking.update();
        }
    }

    //Observer method
    public void update() {
        for (Booking booking: bookings) {
            booking = booking.getState();
        }
    }

}