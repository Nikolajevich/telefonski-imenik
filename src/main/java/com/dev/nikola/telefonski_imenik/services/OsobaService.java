package com.dev.nikola.telefonski_imenik.services;

import com.dev.nikola.telefonski_imenik.models.Osoba;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OsobaService {
    void saveOsoba(Osoba osoba);
    Osoba getOsobaById(Long id);
    void deleteOsobaById(Long id);
    Page<Osoba> getPaginatedParams(int pageNum,
                                   int pageSize,
                                   String oib,
                                   String ime,
                                   String prezime,
                                   String grad,
                                   String broj);
}
