package pl.rj.hikingemergency.model;

import java.util.Vector;

/**
 * Created by radoslawjarzynka on 18.10.2014.
 */
public class Tourist {

    private Vector<Location> locations;
    private String phoneNumber;
    private boolean isInEmergency;

    public Tourist(Vector<Location> locations, String phoneNumber) {
        this.locations = locations;
        this.phoneNumber = phoneNumber;
        this.isInEmergency = false;
    }

    public boolean isInEmergency() {
        return isInEmergency;
    }

    public void setInEmergency(boolean isInEmergency) {
        this.isInEmergency = isInEmergency;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Vector<Location> getLocations() {
        return locations;
    }

    public void setLocations(Vector<Location> locations) {
        this.locations = locations;
    }

}
