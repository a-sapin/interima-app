package stoil.loki.interim;

import java.io.Serializable;
import java.util.Date;

public class Offer implements Serializable {


    private int id;
    private int idEmp;
    private String title;
    private Date datePublication;
    private Date dateFermeture;
    private Date dateDebut;
    private Date dateFin;
    private String url;

    private float salaire;
    float geolat;
    float geolong;

    private String lienImg;

    private String description;

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

    public Offer(int id, int idEmp, String title, Date datePublication, Date dateFermeture, Date dateDebut, Date dateFin, String url, float salaire, float geolat, float geolong, String lienImg, String description) {
        this.id = id;
        this.idEmp = idEmp;
        this.title = title;
        this.datePublication = datePublication;
        this.dateFermeture = dateFermeture;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.url = url;
        this.salaire = salaire;
        this.geolat = geolat;
        this.geolong = geolong;
        this.lienImg = lienImg;
        this.description = description;
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

    public int getIdEmp() {
        return this.idEmp;
    }

    public Date getDatePublication() {
        return this.datePublication;
    }

    public Date getDateFermeture(){
        return this.dateFermeture;
    }

    public Date getDateDebut() {
        return this.dateDebut;
    }

    public Date getDateFin(){
        return this.dateFin;
    }

    public float getSalaire(){
        return this.salaire;
    }

    public String getLienImg(){
        return this.lienImg;
    }

    public String getDescription(){
        return this.description;
    }
}
