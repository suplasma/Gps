package com.example.socialmap;

// TODO: поля можно сделать приватными
public class Coordinates {
    public String user;
    public double latitude;
    public double longitude;

    public Coordinates() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Coordinates(String user, double latitude, double longitude) {
        this.user = user;
        this.latitude = latitude;
        this.longitude = longitude;

    }
}
