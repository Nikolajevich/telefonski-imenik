package com.dev.nikola.telefonski_imenik.wrapper;

public class OsobaPretraga {

    private Long id;

    private String oib;

    private String ime;

    private String prezime;

    private String broj;

    private String grad;

    private String sortField;

    private String sortDir;

    private String path;

    private String sortFieldDetails;

    private String sortDirDetails;

    private String pathDetails;


    public OsobaPretraga() {
        sortField = "prezime";
        sortDir = "asc";
        path = "/";
        pathDetails = "/osobaDetails";
        sortFieldDetails = "grad";
        sortDirDetails = "asc";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getBroj() {
        return broj;
    }

    public void setBroj(String broj) {
        this.broj = broj;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getSortDir() {
        return sortDir;
    }

    public void setSortDir(String sortDir) {
        this.sortDir = sortDir;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSortFieldDetails() {
        return sortFieldDetails;
    }

    public void setSortFieldDetails(String sortFieldDetails) {
        this.sortFieldDetails = sortFieldDetails;
    }

    public String getSortDirDetails() {
        return sortDirDetails;
    }

    public void setSortDirDetails(String sortDirDetails) {
        this.sortDirDetails = sortDirDetails;
    }

    public String getPathDetails() {
        return pathDetails;
    }

    public void setPathDetails(String pathDetails) {
        this.pathDetails = pathDetails;
    }
}
