package com.dev.nikola.telefonski_imenik.repositories;

import com.dev.nikola.telefonski_imenik.models.Osoba;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OsobaRepositoryTest {


    @Autowired
    private OsobaRepository osobaRepository;

    @Test
    void findPaginatedByParams() {
    }

    @Test
    void spremiOsobu() {

        Osoba osoba = new Osoba();

        osoba.setAdresa("asd");
        osoba.setBroj("2");
        osoba.setGrad("gr");
        osoba.setPrezime("Da");
        osoba.setIme("d");
        osoba.setOib("57764165214");

        osobaRepository.save(osoba);

    }
}