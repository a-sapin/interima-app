package stoil.loki.interim;

public class AbonnementData {
    int id;

    String nom;

    float prix;

    String avantages;

    String conditions;

    public AbonnementData(int id, String nom, float prix, String avantages, String conditions) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.avantages = avantages;
        this.conditions = conditions;
    }

    public String getNom() {
        return this.nom;
    }

    public int getId(){
        return this.id;
    }

    public float getPrix(){
        return this.prix;
    }

    public String getAvantages(){
        return this.avantages;
    }

    public String getConditions(){
        return this.conditions;
    }
}
