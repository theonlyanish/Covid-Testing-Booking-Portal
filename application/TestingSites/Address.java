package application.TestingSites;

import application.Bookings.Booking;

import java.util.ArrayList;
import java.util.Hashtable;

public class Address {
    private int latitude;
    private int longitude;
    private String street;
    private String street2;
    private String suburb;
    private String state;
    private String postcode;
    private Hashtable<String, String> additionalInfo;
    private ArrayList<TestingSite> testingSites = new ArrayList<>();
    private Address objectState = this;

    public Address(int latitude, int longitude, String street, String street2, String suburb, String state, String postcode, Hashtable<String, String> additionalInfo) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.street = street;
        this.street2 = street2;
        this.suburb = suburb;
        this.state = state;
        this.postcode = postcode;
        this.additionalInfo = additionalInfo;
    }

    public Address() {
    }

    public String getSuburb() {
        return suburb;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
        setObjectState(this);
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
        setObjectState(this);
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
        setObjectState(this);
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
        setObjectState(this);
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
        setObjectState(this);
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
        setObjectState(this);
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
        setObjectState(this);
    }

    public Hashtable<String, String> getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(Hashtable<String, String> additionalInfo) {
        this.additionalInfo = additionalInfo;
        setObjectState(this);
    }

    @Override
    public String toString() {
        return (this.street + "    " + this.suburb + "   " + this.state + "   " + this.postcode);
    }

    //Observer methods
    public Address getObjectState() {
        return objectState;
    }

    public void setObjectState(Address objectState) {
        this.objectState = objectState;
        notifyAllObservers();
    }

    public void notifyAllObservers(){
        for (TestingSite testingSite : testingSites) {
            testingSite.update();
        }
    }

    public void attach(TestingSite testingSite){
        testingSites.add(testingSite);
    }
}