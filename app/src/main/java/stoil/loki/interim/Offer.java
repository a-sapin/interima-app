package stoil.loki.interim;

import java.io.Serializable;

public class Offer implements Serializable {


    private int id;
    private String title;
    private String url;

    float geolat;
    float geolong;

    double distFromUser;


    public Offer(int id, String title, String url) {
        this.id = id;
        this.title = title;
        this.url = url;

        //DEFAULT VALUES, THIS ISN'T FINAL /!\
        this.geolat = (float) 48.856614;
        this.geolong = (float) 2.3522219;
        this.distFromUser = 0;
    }

    public Offer() {
        this.id = 0;
        this.title = "titre de l'annonce";
        this.url = "url de l'annonce";

        //DEFAULT VALUES, THIS ISN'T FINAL /!\
        this.geolat = (float) 48.856614;
        this.geolong = (float) 2.3522219;
        this.distFromUser = 0;
    }

    public double getDistFromUser() {
        return distFromUser;
    }

    public void setDistFromUser(double distFromUser) {
        this.distFromUser = distFromUser;
    }

    public double calculateDiff(float x1, float y1)
    {
        float deltaX = this.geolong - x1;
        float deltaY = this.geolat - y1;
        double distance = Math.sqrt(((double) deltaX * (double) deltaX) + ((double) deltaY * (double) deltaY));
        this.distFromUser = distance;
        return distance;
    }

    public int getId() {
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getUrl(){
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public float getGeolat() {
        return geolat;
    }

    public void setGeolat(float geolat) {
        this.geolat = geolat;
    }

    public float getGeolong() {
        return geolong;
    }

    public void setGeolong(float geolong) {
        this.geolong = geolong;
    }
}
