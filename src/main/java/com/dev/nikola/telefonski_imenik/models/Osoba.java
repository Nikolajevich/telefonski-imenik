package com.dev.nikola.telefonski_imenik.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "osobe")
public class Osoba {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "OIB")
    @NotEmpty(message = "Polje ne smije biti prazno")
    @Pattern(regexp = "\\d{11}", message = "OIB mora sadržavati 11 znamenki")
    private String oib;

    @Column(name = "ime")
    @NotEmpty(message = "Polje ne smije biti prazno")
    private String ime;

    @Column(name = "prezime")
    @NotEmpty(message = "Polje ne smije biti prazno")
    private String prezime;

    @Column(name = "adresa")
    @NotEmpty(message = "Polje ne smije biti prazno")
    private String adresa;

    @Column(name = "grad")
    @NotEmpty(message = "Polje ne smije biti prazno")
    private String grad;

    @Column(name = "telefonski_broj")
    @NotEmpty(message = "Polje ne smije biti prazno")
    private String broj;

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
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

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getBroj() {
        return broj;
    }

    public void setBroj(String broj) {
        this.broj = broj;
    }
}
