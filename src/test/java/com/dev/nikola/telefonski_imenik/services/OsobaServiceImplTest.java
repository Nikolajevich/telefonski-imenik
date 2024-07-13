package com.dev.nikola.telefonski_imenik.services;

import com.dev.nikola.telefonski_imenik.models.Osoba;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class OsobaServiceImplTest {

    @Autowired
    OsobaService osobaService;

    @Test
    void savingOneOsoba() {
        Osoba osoba = new Osoba();

        osoba.setOib("53619364842");
        osoba.setIme("Petar");
        osoba.setPrezime("Agić");
        osoba.setGrad("Rijeka");
        osoba.setAdresa("Gunduliceva 2");
        osoba.setBroj("0915462349");

        osobaService.saveOsoba(osoba);
        assertNotNull(osobaService.getOsobaById(osoba.getId()));
    }
}