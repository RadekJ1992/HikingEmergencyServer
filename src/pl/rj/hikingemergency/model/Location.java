package pl.rj.hikingemergency.model;

import java.util.Date;

/**
 * Created by radoslawjarzynka on 18.10.2014.
 */
public class Location {
    private int id;
    private float latitude;
    private float longitude;
    private Date date;

    public Location(float longitude, float latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.date = new Date();
    }


    public Location(int id, float latitude, float longitude, Date date) {

        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
    }

    public Location(float longitude, float latitude, Date date) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
