package com.example.hayounglee.xml_test;

public class EqInfo {
    private double lat;
    private double lon;
    private double mt;
    private String img;
    private String tmEqk;
    private String loc;
    private String rem;

    public void setRem(String rem) {

        if (rem == null || rem.equals("") == true) rem = "특이사항 없음";
        this.rem = rem;
    }

    public String getRem() {
        return rem;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setMt(double mt) {
        this.mt = mt;
    }

    public void setTmEqk(String tmEqk) {
        this.tmEqk = tmEqk;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public double getMt() {
        return mt;
    }

    public String getImg() {
        return img;
    }

    public String getTmEqk() {
        return tmEqk;
    }

    public String getLoc() {
        return loc;
    }
}
