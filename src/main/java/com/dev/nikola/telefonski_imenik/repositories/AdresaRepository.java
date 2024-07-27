package com.dev.nikola.telefonski_imenik.repositories;

import com.dev.nikola.telefonski_imenik.models.Adresa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdresaRepository extends JpaRepository<Adresa, Long> {

    @Query("SELECT a FROM Adresa a WHERE a.osoba.id = :osobaId")
    Page<Adresa> findByOsobaId(@Param("osobaId") Long osobaId, Pageable pageable);

}
