package stoil.loki.interim;

public class UserData {

    private String nom, prenom, email, nat, tel, nomServDept, nomSousSD, siret, nom2, email2, tel2, adresse, nomEntreprise;

    // Pour chercheur d'emploi
    public UserData(String nom, String prenom, String email, String nat, String tel) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.nat = nat;
        this.tel = tel;
    }

    // Pour employeur
    public UserData(String nom, String email, String tel, String nomServDept, String nomSousSD, String siret, String nom2, String email2, String tel2, String adresse, String nomEntreprise) {
        this.nom = nom;
        this.email = email;
        this.tel = tel;
        this.nomServDept = nomServDept;
        this.nomSousSD = nomSousSD;
        this.siret = siret;
        this.nom2 = nom2;
        this.email2 = email2;
        this.tel2 = tel2;
        this.adresse = adresse;
        this.nomEntreprise = nomEntreprise;
    }

    // Pour agence d'intérim
    public UserData(String nom, String email, String tel, String siret, String nom2, String email2, String tel2, String adresse, String nomEntreprise) {
        this.nom = nom;
        this.email = email;
        this.tel = tel;
        this.siret = siret;
        this.nom2 = nom2;
        this.email2 = email2;
        this.tel2 = tel2;
        this.adresse = adresse;
        this.nomEntreprise = nomEntreprise;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNat() {
        return nat;
    }

    public void setNat(String nat) {
        this.nat = nat;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getNomServDept() {
        return nomServDept;
    }

    public void setNomServDept(String nomServDept) {
        this.nomServDept = nomServDept;
    }

    public String getNomSousSD() {
        return nomSousSD;
    }

    public void setNomSousSD(String nomSousSD) {
        this.nomSousSD = nomSousSD;
    }

    public String getSiret() {
        return siret;
    }

    public void setSiret(String siret) {
        this.siret = siret;
    }

    public String getNom2() {
        return nom2;
    }

    public void setNom2(String nom2) {
        this.nom2 = nom2;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNomEntreprise() {
        return nomEntreprise;
    }

    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }
}
