package com.dev.nikola.telefonski_imenik.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "osobe")
public class Osoba {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Polje ne smije biti prazno")
    @Pattern(regexp = "\\d{11}", message = "OIB mora sadržavati 11 znamenki")
    private String oib;

    @NotEmpty(message = "Polje ne smije biti prazno")
    private String ime;

    @NotEmpty(message = "Polje ne smije biti prazno")
    private String prezime;

    @Column(name = "telefonski_broj")
    @NotEmpty(message = "Polje ne smije biti prazno")
    private String broj;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "osoba", orphanRemoval = true)
    private List<Adresa> adresaList = new ArrayList<>();

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Adresa> getAdresaList() {
        return adresaList;
    }

    public void addAdresa(Adresa adresa) {
        adresa.setOsoba(this);
        adresaList.add(adresa);
    }

    public void deleteAdresa(Adresa adresa) {
        adresaList.remove(adresa);
    }

    public void setAdresaList(List<Adresa> adresaList) {
        if (!adresaList.isEmpty()) {
            for (Adresa adresa : adresaList) {
                adresa.setOsoba(this);
            }
        }
        this.adresaList = adresaList;
    }
}
