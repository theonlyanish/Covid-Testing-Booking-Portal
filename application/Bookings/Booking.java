package application.Bookings;

import java.util.Date;
import java.util.Hashtable;
import java.util.Random;

import application.Observer;
import application.Tests.Covid_Test;
import application.TestingSites.TestingSite;
import application.Users.Customer;

//Following are declarations of all attributes of booking class
public class Booking extends Observer {

    Random r = new Random();

    private String id;
    private Customer customer;
    private TestingSite testingSite;
    private Date createdAt;
    private Date updatedAt;
    private String smsPin;
    private String status;
    private Covid_Test[] covidTests;
    private String notes;
    private Hashtable<String, String> additionalInfo;
    private QRCode qrCode;
    private Booking state;

    public Booking(String id, Customer customer, TestingSite testingSite, Date createdAt, Date updatedAt,
                   String smsPin, String status, Covid_Test[] covidTests, String notes,
                   Hashtable<String, String> additionalInfo) {
        this.id = id;
        this.customer = customer;
        this.testingSite = testingSite;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.smsPin = smsPin;
        this.status = status;
        this.covidTests = covidTests;
        this.notes = notes;
        this.additionalInfo = additionalInfo;
        this.qrCode = generateQRCode();
        //this.testingSite.attach(this); //observer
        setState(this);
    }

    public QRCode generateQRCode() {
        return new QRCode(this.id);
    }

    public String generateURL() {
        return ("http://bit.ly/" + String.valueOf(r.nextInt()));
    }

    // Following are getter and setters for all attributes in this class
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        setState(this);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        setState(this);
    }

    public TestingSite getTestingSite() {
        return testingSite;
    }

    public void setTestingSite(TestingSite testingSite) {
        this.testingSite = testingSite;
        setState(this);
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        setState(this);
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        setState(this);
    }

    public String getSmsPin() {
        return smsPin;
    }

    public void setSmsPin(String smsPin) {
        this.smsPin = smsPin;
        setState(this);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        setState(this);
    }

    public Covid_Test[] getCovidTests() {
        return covidTests;
    }

    public void setCovidTests(Covid_Test[] covidTests) {
        this.covidTests = covidTests;
        setState(this);
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
        setState(this);
    }

    public Hashtable<String, String> getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(Hashtable<String, String> additionalInfo) {
        this.additionalInfo = additionalInfo;
        setState(this);
    }

    public QRCode getQrCode() {
        return qrCode;
    }

    public void setQrCode(QRCode qrCode) {
        this.qrCode = qrCode;
        setState(this);
    }

    //Observer method
    @Override
    public void update() {
        this.customer = customer.getState();
        this.testingSite = testingSite.getState();
    }

    //Observer Subject Methods
    public Booking getState() {
        return state;
    }

    public void setState(Booking state) {
        this.state = state;
        notifyAllObservers();
    }

    public void notifyAllObservers(){
        customer.update();
    }

}