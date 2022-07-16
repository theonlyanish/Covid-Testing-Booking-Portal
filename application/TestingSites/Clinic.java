package application.TestingSites;


public class Clinic extends TestingSite {

    private Long openingTime = Long.valueOf(9);
    private Long closingTime = Long.valueOf(18);

    public Clinic(String id, String name, String description, String websiteUrl, String phoneNumber, Address address, FacilityType facilityType) {
        super(id, name, description, websiteUrl, phoneNumber, address, facilityType);
        this.setOpeningTime(Long.valueOf(9));
        this.setClosingTime(Long.valueOf(18));
    }

    public Clinic() {
    }

    @Override
    public Boolean providesOnsiteBooking() {
        return true;
    }
}