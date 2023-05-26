package stoil.loki.interim;

import java.io.Serializable;

public class Offer implements Serializable {


    private int id;
    private String title;
    private String url;

    public Offer(int id, String title, String url) {
        this.id = id;
        this.title = title;
        this.url = url;
    }

    public Offer() {
        this.id = 0;
        this.title = "titre de l'annonce";
        this.url = "url de l'annonce";
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

}
