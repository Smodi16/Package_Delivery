package com.example.user.package_delivery;

public class User {
    public double latitude;
    public double longitude;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(double latitude, double longitude) {
        this.latitude=latitude;
        this.longitude=longitude;
    }

}

