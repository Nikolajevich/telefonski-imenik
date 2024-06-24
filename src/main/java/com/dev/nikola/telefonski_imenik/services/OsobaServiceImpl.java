package com.dev.nikola.telefonski_imenik.services;

import com.dev.nikola.telefonski_imenik.models.Osoba;
import com.dev.nikola.telefonski_imenik.repositories.OsobaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OsobaServiceImpl implements  OsobaService{

    private final OsobaRepository osobaRepository;

    public OsobaServiceImpl(OsobaRepository osobaRepository) {
        this.osobaRepository = osobaRepository;
    }

    @Override
    @Transactional
    public void saveOsoba(Osoba osoba) {
        this.osobaRepository.save(osoba);
    }

    @Override
    @Transactional(readOnly = true)
    public Osoba getOsobaById(Long id) {
        Optional<Osoba> optional = this.osobaRepository.findById(id);
        Osoba osoba = null;
        if (optional.isPresent()) {
            osoba = optional.get();
        } else {
            throw new RuntimeException(" Osoba nije nađena za id :: " + id);
        }
        return osoba;
    }

    @Override
    @Transactional
    public void deleteOsobaById(Long id) {
        this.osobaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Osoba> getPaginatedParams(int pageNum,
                                          int pageSize,
                                          String oib,
                                          String ime,
                                          String prezime,
                                          String grad,
                                          String broj) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        return this.osobaRepository.findPaginatedByParams(oib, ime, prezime, grad, broj, pageable);
    }

}
