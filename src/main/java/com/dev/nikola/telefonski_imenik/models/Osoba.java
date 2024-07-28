package com.dev.nikola.telefonski_imenik.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.HashSet;
import java.util.Set;

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
    private Set<Adresa> adresaSet = new HashSet<>();

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

    public Set<Adresa> getAdresaSet() {
        return adresaSet;
    }

    public void addAdresa(Adresa adresa) {
        adresa.setOsoba(this);
        adresaSet.add(adresa);
    }

    public void deleteAdresa(Adresa adresa) {
        adresaSet.remove(adresa);
    }

    public void setAdresaSet(Set<Adresa> adresaSet) {
        if (!adresaSet.isEmpty()) {
            for (Adresa adresa : adresaSet) {
                adresa.setOsoba(this);
            }
        }
        this.adresaSet = adresaSet;
    }
}
