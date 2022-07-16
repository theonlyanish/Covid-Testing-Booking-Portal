package application.TestingSites;

import application.Bookings.Booking;
import application.Observer;

import java.util.ArrayList;

//declaration for testingsite attributes
public abstract class TestingSite extends Observer {
    private String id;
    private String name;
    private String description;
    private String websiteUrl;
    private String phoneNumber;
    private Address address;
    private FacilityType facilityType;
    private Long openingTime;
    private Long closingTime;
    private TestingSite state = this;
    private ArrayList<Booking> bookings = new ArrayList<>();

    public TestingSite(String id, String name, String description, String websiteUrl, String phoneNumber, Address address, FacilityType facilityType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.websiteUrl = websiteUrl;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.facilityType = facilityType;
        this.address.attach(this);
        setState(this);
    }

    public TestingSite() { }

// getters and setters for testingsites attribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        setState(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        setState(this);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        setState(this);
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
        setState(this);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        setState(this);
    }

    public void setAddress(Address address) {
        this.address = address;
        setState(this);
    }

    public void setFacilityType(FacilityType facilityType) {
        this.facilityType = facilityType;
        setState(this);
    }

    public Address getAddress() {
        return address;
    }

    public FacilityType getFacilityType() {
        return facilityType;
    }

    public Long getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(Long openingTime) {
        this.openingTime = openingTime;
        setState(this);
    }

    public Long getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(Long closingTime) {
        this.closingTime = closingTime;
        setState(this);
    }

    public Boolean providesOnsiteBooking() {
        return false;
    }

    public Boolean isOpen() {
        // This function is used to check if the testing site is open or not
        // Input - site
        // Output - boolean value if current value falls between opening time and closing time
        if (this.getOpeningTime() == null) {
            this.setOpeningTime(Long.valueOf(0));
        }
        if (this.getClosingTime() == null) {
            this.setClosingTime(Long.valueOf(24));
        }
        Long currTime = System.currentTimeMillis();
        return (currTime > inMilliseconds(this.getOpeningTime()) && currTime < inMilliseconds(this.getClosingTime()));
    }

    private Long inMilliseconds(Long time) {
        return time * 3600000;
    }

    @Override
    public String toString() {
        return (this.name + "   " + this.facilityType + "   " +
                "   Provides Onsite Booking: " + this.providesOnsiteBooking() +
                "   Open Now: " + this.isOpen() +
                "   " + this.address.toString());
//        return (this.name + "   " + this.description + "   " + this.websiteUrl + "   " + this.phoneNumber +
//                "   " + this.address.toString() + "   " + this.facilityType);
    }

    //Observer methods
    public TestingSite getState() {
        return state;
    }

    public void setState(TestingSite state) {
        this.state = state;
        notifyAllObservers();
    }

    public void notifyAllObservers(){
        for (Booking booking : bookings) {
            booking.update();
        }
    }

    public void attach(Booking booking){
        bookings.add(booking);
    }

    //Observer method
    @Override
    public void update() {
        this.address = address.getObjectState();
    }

}