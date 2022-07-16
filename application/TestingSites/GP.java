package application.TestingSites;

public class GP extends TestingSite {

    private Long openingTime = Long.valueOf(11);
    private Long closingTime = Long.valueOf(17);

    public GP(String id, String name, String description, String websiteUrl, String phoneNumber, Address address, FacilityType facilityType) {
        super(id, name, description, websiteUrl, phoneNumber, address, facilityType);
        this.setOpeningTime(Long.valueOf(11));
        this.setClosingTime(Long.valueOf(17));
    }

    public GP() {
    }

    @Override
    public Boolean providesOnsiteBooking() {
        return false;
    }

}