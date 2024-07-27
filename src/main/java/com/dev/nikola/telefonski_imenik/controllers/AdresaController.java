package com.dev.nikola.telefonski_imenik.controllers;

import com.dev.nikola.telefonski_imenik.services.AdresaService;
import org.springframework.stereotype.Controller;

@Controller
public class AdresaController {

    private final AdresaService adresaService;

    public AdresaController(AdresaService adresaService) {
        this.adresaService = adresaService;
    }


}
