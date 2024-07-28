package com.dev.nikola.telefonski_imenik.wrapper;

import com.dev.nikola.telefonski_imenik.models.Adresa;
import com.dev.nikola.telefonski_imenik.models.Osoba;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

public class OsobaAdresaForm {

    @Valid
    private Osoba osoba;
    
    @Valid
    private List<Adresa> adresaList = new ArrayList<>();

    public Osoba getOsoba() {
        return osoba;
    }

    public void setOsoba(Osoba osoba) {
        this.osoba = osoba;
    }

    public List<Adresa> getAdresaList() {
        return adresaList;
    }

    public void setAdresaList(List<Adresa> adresaList) {
        this.adresaList = adresaList;
    }

    public void addAdresa(Adresa adresa) {
        adresaList.add(adresa);
    }

    public void deleteAdresa(Adresa adresa) {
        adresaList.remove(adresa);
    }
}
