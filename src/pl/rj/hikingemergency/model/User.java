package pl.rj.hikingemergency.model;

import java.util.Vector;

/**
 * Created by radoslawjarzynka on 18.10.2014.
 */
public class User {

    private int userID;
    private Vector<Location> locations;
    private String phoneNumber;
    private String emergencyPhoneNumber;
    private boolean isInEmergency;


    public User(int userID, String phoneNumber, String emergencyPhoneNumber) {
        locations = new Vector<>();
        this.userID = userID;
        this.phoneNumber = phoneNumber;
        this.emergencyPhoneNumber = emergencyPhoneNumber;
    }

    public User(int userID, Vector<Location> locations, String phoneNumber, String emergencyPhoneNumber) {
        this.userID = userID;
        this.locations = locations;
        this.phoneNumber = phoneNumber;
        this.emergencyPhoneNumber = emergencyPhoneNumber;
    }

    public User(String phoneNumber, String emergencyPhoneNumber) {
        locations = new Vector<>();
        this.phoneNumber = phoneNumber;
        this.emergencyPhoneNumber = emergencyPhoneNumber;
    }

    public User(String phoneNumber) {
        locations = new Vector<>();
        this.phoneNumber = phoneNumber;
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

    public void addLocation(float latitude, float longitude) {
        locations.add(new Location(latitude, longitude));
    }

    public void setLocations(Vector<Location> locations) {
        this.locations = locations;
    }

    public String getEmergencyPhoneNumber() {
        return emergencyPhoneNumber;
    }

    public void setEmergencyPhoneNumber(String emergencyPhoneNumber) {
        this.emergencyPhoneNumber = emergencyPhoneNumber;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

}
