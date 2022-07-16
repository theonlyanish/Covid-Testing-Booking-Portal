package application.Tests;

import java.util.Date;
import java.util.Hashtable;

import application.Bookings.Booking;
import application.TestTypes.TestType;
import application.Users.Customer;
import application.Users.User;

public class Covid_Test {
    private String id;
    private TestType type;
    private Customer patient;
    private User administerer;
    private Booking booking;
    private TestResult result;
    private String status;
    private String notes;
    private String datePerformed;
    private String dateOfResults;
    private Date createdAt;
    private Date updatedAt;
    private Hashtable<String, String> additionalInfo;

    public Covid_Test(String id, TestType type, Customer patient, User administerer, Booking booking, TestResult result, String status, String notes, String datePerformed, String dateOfResults, Date createdAt, Date updatedAt, Hashtable<String, String> additionalInfo) {
        this.id = id;
        this.type = type;
        this.patient = patient;
        this.administerer = administerer;
        this.booking = booking;
        this.result = result;
        this.status = status;
        this.notes = notes;
        this.datePerformed = datePerformed;
        this.dateOfResults = dateOfResults;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.additionalInfo = additionalInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TestType getType() {
        return type;
    }

    public void setType(TestType type) {
        this.type = type;
    }

    public Customer getPatient() {
        return patient;
    }

    public void setPatient(Customer patient) {
        this.patient = patient;
    }

    public User getAdministerer() {
        return administerer;
    }

    public void setAdministerer(User administerer) {
        this.administerer = administerer;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public TestResult getResult() {
        return result;
    }

    public void setResult(TestResult result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDatePerformed() {
        return datePerformed;
    }

    public void setDatePerformed(String datePerformed) {
        this.datePerformed = datePerformed;
    }

    public String getDateOfResults() {
        return dateOfResults;
    }

    public void setDateOfResults(String dateOfResults) {
        this.dateOfResults = dateOfResults;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Hashtable<String, String> getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(Hashtable<String, String> additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}