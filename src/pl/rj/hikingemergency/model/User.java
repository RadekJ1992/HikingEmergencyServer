package pl.rj.hikingemergency.model;

import java.util.Vector;

/**
 * Obiekt przechowujący dane użytkownika
 * Created by radoslawjarzynka on 18.10.2014.
 */
public class User {

    /**
     * identyfikator użytkownika w bazie
     */
    private int userID;
    /**
     * wektor lokalizacji użytkownika
     */
    private Vector<Location> locations;
    /**
     * numer telefonu użytkownika
     */
    private String phoneNumber;
    /**
     * numer awaryjny
     */
    private String emergencyPhoneNumber;
    /**
     * flaga informująca o tym, czy użytkownik jest w niebezpieczeństwie
     */
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

    public void addLocation(Location location) {
        locations.add(location);
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
