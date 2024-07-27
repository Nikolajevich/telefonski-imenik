package com.dev.nikola.telefonski_imenik.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Adresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ulicni_broj")
    @NotEmpty(message = "Polje ne smije biti prazno")
    private String ulicniBroj;

    @NotEmpty(message = "Polje ne smije biti prazno")
    private String ulica;

    @NotEmpty(message = "Polje ne smije biti prazno")
    private String grad;

    @ManyToOne
    @JoinColumn(name = "osoba_id")
    private Osoba osoba;

    public String getUlicniBroj() {
        return ulicniBroj;
    }

    public void setUlicniBroj(String ulicniBroj) {
        this.ulicniBroj = ulicniBroj;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public Osoba getOsoba() {
        return osoba;
    }

    public void setOsoba(Osoba osoba) {
        this.osoba = osoba;
    }
}
