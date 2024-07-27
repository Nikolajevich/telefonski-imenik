package com.dev.nikola.telefonski_imenik.services;


import com.dev.nikola.telefonski_imenik.models.Adresa;
import com.dev.nikola.telefonski_imenik.repositories.AdresaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AdresaServiceImpl implements AdresaService {

    private final AdresaRepository adresaRepository;

    public AdresaServiceImpl(AdresaRepository adresaRepository) {
        this.adresaRepository = adresaRepository;
    }

    @Override
    @Transactional
    public void saveAdresa(Adresa adresa) {
        adresaRepository.save(adresa);
    }

    @Override
    @Transactional(readOnly = true)
    public Adresa getAdresaById(Long id) {
        Optional<Adresa> optional = adresaRepository.findById(id);
        Adresa adresa = null;
        if (optional.isPresent()) {
            adresa = optional.get();
        } else {
            throw new RuntimeException(" Adresa nije nađena za id :: " + id);
        }
        return adresa;
    }

    @Override
    @Transactional
    public void deleteAdresaById(Long id) {
        adresaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Adresa> getPaginatedAdresa(int pageNum, int pageSize, String sortField, String sortDirection, Long osobaId) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        return adresaRepository.findByOsobaId(osobaId, pageable);
    }
}
