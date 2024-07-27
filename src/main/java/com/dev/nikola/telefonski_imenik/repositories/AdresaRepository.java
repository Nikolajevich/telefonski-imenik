package com.dev.nikola.telefonski_imenik.repositories;

import com.dev.nikola.telefonski_imenik.models.Adresa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdresaRepository extends JpaRepository<Adresa, Long> {

    Page<Adresa> findByOsobaId(Long osobaId, Pageable pageable);

}
