package application.TestingSites;

public class Hospital extends TestingSite {

    private Long openingTime = Long.valueOf(0);
    private Long closingTime = Long.valueOf(24);

    public Hospital(String id, String name, String description, String websiteUrl, String phoneNumber, Address address, FacilityType facilityType) {
        super(id, name, description, websiteUrl, phoneNumber, address, facilityType);
        this.setOpeningTime(Long.valueOf(0));
        this.setClosingTime(Long.valueOf(24));
    }

    public Hospital() {
    }

    @Override
    public Boolean providesOnsiteBooking() {
        return true;
    }
}