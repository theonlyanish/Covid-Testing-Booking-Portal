package application.TestingSites;

import application.ServiceClient;
import application.Services.Service;

public class UserHome extends TestingSite {

    private Long openingTime = Long.valueOf(0);
    private Long closingTime = Long.valueOf(24);

    public UserHome(String id, String name, String description, String websiteUrl, String phoneNumber, Address address, FacilityType facilityType) {
        super(id, name, description, websiteUrl, phoneNumber, address, facilityType);
        this.setOpeningTime(Long.valueOf(0));
        this.setClosingTime(Long.valueOf(24));
    }

    public UserHome() {
    }

    @Override
    public Boolean providesOnsiteBooking() {
        return false;
    }

    public String getHomeId() {
        TestingSite[] testingSites = new TestingSite[]{};
        try {
            testingSites = ServiceClient.getAllTestingSites();
        } catch (Exception e) {
            System.out.println(e);
        }

        for (int i = 0; i < testingSites.length; i ++) {
            if (testingSites[i].getName() != null && this.getName().equals(testingSites[i].getName())) {
                return testingSites[i].getId();
            }

        }
        return "";
    }
}