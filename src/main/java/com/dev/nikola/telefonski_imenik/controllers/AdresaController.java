package com.dev.nikola.telefonski_imenik.controllers;

import com.dev.nikola.telefonski_imenik.models.Adresa;
import com.dev.nikola.telefonski_imenik.models.Osoba;
import com.dev.nikola.telefonski_imenik.services.AdresaService;
import com.dev.nikola.telefonski_imenik.services.OsobaService;
import com.dev.nikola.telefonski_imenik.wrapper.OsobaPretraga;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdresaController {

    private final AdresaService adresaService;
    private final OsobaService osobaService;

    public AdresaController(AdresaService adresaService, OsobaService osobaService) {
        this.adresaService = adresaService;
        this.osobaService = osobaService;
    }

    public String osobaDetails(OsobaPretraga osobaPretraga, int pageNum, Model model) {
        int pageSize = 4;

        Page<Adresa> page = adresaService.getPaginatedAdresa(pageNum,
                pageSize,
                osobaPretraga.getSortFieldDetails(),
                osobaPretraga.getSortDirDetails(),
                osobaPretraga.getId());

        List<Adresa> listAdrese = page.getContent();

        model.addAttribute("osobaPretraga", osobaPretraga);

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("reverseSortDir", osobaPretraga.getSortDirDetails().equals("asc") ? "desc" : "asc");

        model.addAttribute("listAdrese", listAdrese);

        model.addAttribute("osoba", osobaService.getOsobaById(osobaPretraga.getId()));
        return "osoba_details";
    }

    @GetMapping("/osobaDetails")
    public String initOsobaDetails(@SessionAttribute("osobaPretraga") OsobaPretraga osobaPretraga, Model model) {
        osobaPretraga.setPathDetails("/osobaDetails");
        return osobaDetails(osobaPretraga, 1, model);
    }

    @GetMapping("/osobaDetails/page/{pageNum}")
    public String paginateOsobaDetails(@SessionAttribute("osobaPretraga") OsobaPretraga osobaPretraga,
                                       @PathVariable(value = "pageNum") int pageNum,
                                       Model model) {
        osobaPretraga.setPathDetails("/osobaDetails/page/" + pageNum);
        return osobaDetails(osobaPretraga, pageNum, model);
    }

    @GetMapping("/osobaDetails/sort{pageNum}")
    public String sortOsobaDetails(@SessionAttribute("osobaPretraga") OsobaPretraga osobaPretraga,
                                   @PathVariable(value = "pageNum") int pageNum,
                                   @RequestParam("sortField") String sortField,
                                   @RequestParam("sortDir") String sortDir,
                                   Model model) {
        osobaPretraga.setSortFieldDetails(sortField);
        osobaPretraga.setSortDirDetails(sortDir);
        return osobaDetails(osobaPretraga, pageNum, model);
    }

    @GetMapping("/addAdresa")
    public String addAdresaForm(@SessionAttribute("osobaPretraga") OsobaPretraga osobaPretraga, Model model) {
        model.addAttribute("adresa", new Adresa());
        model.addAttribute("osobaPretraga", osobaPretraga);
        return "adresa_form";
    }

    @PostMapping("/addAdresa")
    public String addAdresa(@SessionAttribute("osobaPretraga") OsobaPretraga osobaPretraga,
                            @Valid @ModelAttribute("adresa") Adresa adresa,
                            BindingResult result,
                            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("osobaPretraga", osobaPretraga);
            return "adresa_form";
        }

        Osoba osoba = osobaService.getOsobaById(osobaPretraga.getId());
        osoba.addAdresa(adresa);
        adresaService.saveAdresa(adresa);
        model.addAttribute("saveSuccess", true);
        model.addAttribute("adresa", new Adresa());
        model.addAttribute("osobaPretraga", osobaPretraga);
        return "adresa_form";
    }

    @GetMapping("/updateAdresaForm/{id}")
    public String updateOsobaForm(@SessionAttribute("osobaPretraga") OsobaPretraga osobaPretraga,
                                  @PathVariable(value = "id") Long id,
                                  Model model) {
        model.addAttribute("adresa", adresaService.getAdresaById(id));
        model.addAttribute("osobaPretraga", osobaPretraga);
        return "adresa_form";
    }

    @PostMapping("/updateAdresaForm/{id}")
    public String updateOsoba(@SessionAttribute("osobaPretraga") OsobaPretraga osobaPretraga,
                              @Valid @ModelAttribute("adresa") Adresa formAdresa,
                              BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("osobaPretraga", osobaPretraga);
            return "adresa_form";
        }

        Adresa adresa = adresaService.getAdresaById(formAdresa.getId());
        adresa.setGrad(formAdresa.getGrad());
        adresa.setUlica(formAdresa.getUlica());
        adresa.setUlicniBroj(formAdresa.getUlicniBroj());
        adresaService.saveAdresa(adresa);
        model.addAttribute("updateSuccess", true);
        model.addAttribute("osobaPretraga", osobaPretraga);
        return "adresa_form";
    }

    @GetMapping("/deleteAdresa/{id}")
    public String deleteOsoba(@PathVariable(value = "id") Long id,
                              @SessionAttribute("osobaPretraga") OsobaPretraga osobaPretraga) {
        adresaService.deleteAdresaById(id);
        return "redirect:" + osobaPretraga.getPathDetails();
    }
}
