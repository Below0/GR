package com.example.hayounglee.xml_test;

public class ShelterInfo {
    private String lat;
    private String lon;
    private String name;
    private String address;
    private int distance;

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public String getName() {
        return name;
    }

    public int getDistance() {
        return distance;
    }
}
