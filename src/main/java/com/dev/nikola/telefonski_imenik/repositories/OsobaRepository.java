package com.dev.nikola.telefonski_imenik.repositories;

import com.dev.nikola.telefonski_imenik.models.Osoba;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OsobaRepository extends JpaRepository<Osoba, Long> {

    @Query("SELECT o FROM Osoba o WHERE (:oib IS NULL OR :oib = '' OR o.oib = :oib)" +
            "                       AND (:ime IS NULL OR :ime = '' OR o.ime = :ime)" +
            "                       AND (:prezime IS NULL OR :prezime = '' OR o.prezime = :prezime)" +
            "                       AND (:grad IS NULL OR :grad = '' OR o.grad = :grad)" +
            "                       AND (:broj IS NULL OR :broj = '' OR o.broj = :broj)")
    Page<Osoba> findPaginatedByParams(@Param("oib") String oib,
                             @Param("ime") String ime,
                             @Param("prezime") String prezime,
                             @Param("grad") String grad,
                             @Param("broj") String broj,
                             Pageable pageable);

}
