package org.md.Geolocalizzazione;

public class Circumference {
    private int latitude = 0;
    private int longitude = 0;
    private int Radius = 0;
    private String cittá = "";

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public int getRadius() {
        return Radius;
    }

    public void setRadius(int radius) {
        Radius = radius;
    }

    public String getCittá() {
        return cittá;
    }

    public void setCittá(String cittá) {
        this.cittá = cittá;
    }
}
