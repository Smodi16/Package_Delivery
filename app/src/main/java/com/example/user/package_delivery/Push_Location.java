package com.example.user.package_delivery;

public class Push_Location {
    String loactionid;
    double latitude;
    double longitude;

    public Push_Location(String loactionid, double latitude, double longitude) {
        this.loactionid = loactionid;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setLoactionid(String loactionid) {
        this.loactionid = loactionid;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
