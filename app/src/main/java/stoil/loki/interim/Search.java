package stoil.loki.interim;

import java.util.Date;

public class Search {

    private int id;
    private int idUti;
    private String searchstr;
    private Date datesearch;

    public Search(int id, int idUti, String searchstr, Date datesearch) {
        this.id = id;
        this.idUti = idUti;
        this.searchstr = searchstr;
        this.datesearch = datesearch;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUti() {
        return idUti;
    }

    public void setIdUti(int idUti) {
        this.idUti = idUti;
    }

    public String getSearchstr() {
        return searchstr;
    }

    public void setSearchstr(String searchstr) {
        this.searchstr = searchstr;
    }

    public Date getDatesearch() {
        return datesearch;
    }

    public void setDatesearch(Date datesearch) {
        this.datesearch = datesearch;
    }
}
