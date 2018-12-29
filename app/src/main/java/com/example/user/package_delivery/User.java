package com.example.user.package_delivery;

public class User {
    public double latitude;
    public double longitude;
    public String locationid;

    public User()
    {

    }

    public User(double latitude, double longitude, String locationid) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.locationid = locationid;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getLocationid() {
        return locationid;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLocationid(String locationid) {
        this.locationid = locationid;
    }
}

