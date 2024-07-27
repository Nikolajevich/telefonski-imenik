package com.dev.nikola.telefonski_imenik.services;

import com.dev.nikola.telefonski_imenik.models.Adresa;
import org.springframework.data.domain.Page;

public interface AdresaService {

    void saveAdresa(Adresa adresa);

    Adresa getAdresaById(Long id);

    void deleteAdresaById(Long id);

    Page<Adresa> getPaginatedAdresa(int pageNum,
                                    int pageSize,
                                    String sortField,
                                    String sortDirection,
                                    Long id);

}
