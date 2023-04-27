package stoil.loki.interim;

import java.io.Serializable;

public class CandidatureData implements Serializable {
    private String title;

    public CandidatureData(String title) {
        this.title = title;
    }

    public CandidatureData() {
        this.title = "titre de la candidature";
    }

    public String getTitle(){
        return title;
    }

}
