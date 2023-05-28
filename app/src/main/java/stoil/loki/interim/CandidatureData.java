package stoil.loki.interim;

import java.io.Serializable;

public class CandidatureData implements Serializable {

    private int id, idUti, idOffre;
    private String lienCV, lienLM, commentaires, statut, offertitle;

    public CandidatureData(String title) {
        this.offertitle = title;
    }

    public CandidatureData() {
        this.offertitle = "titre de la candidature";
    }

    public CandidatureData(int id, int idUti, int idOffre, String lienCV, String lienLM, String commentaires, String statut, String offertitle) {
        this.id = id;
        this.idUti = idUti;
        this.idOffre = idOffre;
        this.lienCV = lienCV;
        this.lienLM = lienLM;
        this.commentaires = commentaires;
        this.statut = statut;
        this.offertitle = offertitle;
    }

    public int getId() {
        return id;
    }

    public int getIdUti() {
        return idUti;
    }

    public int getIdOffre() {
        return idOffre;
    }

    public String getLienCV() {
        return lienCV;
    }

    public String getLienLM() {
        return lienLM;
    }

    public String getCommentaires() {
        return commentaires;
    }

    public String getStatut() {
        return statut;
    }

    public String getOffertitle() {
        return offertitle;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdUti(int idUti) {
        this.idUti = idUti;
    }

    public void setIdOffre(int idOffre) {
        this.idOffre = idOffre;
    }

    public void setLienCV(String lienCV) {
        this.lienCV = lienCV;
    }

    public void setLienLM(String lienLM) {
        this.lienLM = lienLM;
    }

    public void setCommentaires(String commentaires) {
        this.commentaires = commentaires;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setOffertitle(String offertitle) {
        this.offertitle = offertitle;
    }
}
