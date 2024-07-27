package com.dev.nikola.telefonski_imenik.controllers;

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
@SessionAttributes("osobaPretraga")
public class OsobaController {

    private final OsobaService osobaService;
    private final AdresaService adresaService;

    public OsobaController(OsobaService osobaService, AdresaService adresaService) {
        this.osobaService = osobaService;
        this.adresaService = adresaService;
    }

    @ModelAttribute("osobaPretraga")
    public OsobaPretraga initOsobaPretraga() {
        return new OsobaPretraga();
    }

    public String paginatedHomePage(OsobaPretraga osobaPretraga, int pageNum, Model model) {
        int pageSize = 4;

        Page<Osoba> page = osobaService.getPaginatedParams(pageNum,
                pageSize,
                osobaPretraga.getOib(),
                osobaPretraga.getIme(),
                osobaPretraga.getPrezime(),
                osobaPretraga.getBroj(),
                osobaPretraga.getGrad(),
                osobaPretraga.getSortField(),
                osobaPretraga.getSortDir());

        List<Osoba> listOsobe = page.getContent();

        model.addAttribute("osobaPretraga", osobaPretraga);

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("reverseSortDir", osobaPretraga.getSortDir().equals("asc") ? "desc" : "asc");

        model.addAttribute("listOsobe", listOsobe);
        return "index";
    }

    @RequestMapping({"/", "/sort{pageNum}", "/page/{pageNum}"})
    public String setupHomePage(@ModelAttribute("osobaPretraga") OsobaPretraga osobaPretraga, Model model) {
        osobaPretraga.setPath("/");
        return paginatedHomePage(osobaPretraga, 1, model);
    }

    @GetMapping("/sort{pageNum}")
    public String sortHomePage(@PathVariable(value = "pageNum") int pageNum,
                               @RequestParam("sortField") String sortField,
                               @RequestParam("sortDir") String sortDir,
                               @ModelAttribute("osobaPretraga") OsobaPretraga osobaPretraga,
                               Model model) {
        osobaPretraga.setSortField(sortField);
        osobaPretraga.setSortDir(sortDir);
        return paginatedHomePage(osobaPretraga, pageNum, model);
    }

    @GetMapping("/page/{pageNum}")
    public String startPaginatedHomePage(@PathVariable(value = "pageNum") int pageNum,
                                         @ModelAttribute("osobaPretraga") OsobaPretraga osobaPretraga,
                                         Model model) {
        osobaPretraga.setPath("/page/" + pageNum);
        return paginatedHomePage(osobaPretraga, pageNum, model);
    }

    @GetMapping("/novaOsobaForm")
    public String novaOsobaForm(Model model) {
        model.addAttribute("osoba", new Osoba());
        return "osoba_form";
    }

    @PostMapping("/novaOsobaForm")
    public String saveOsoba(@Valid Osoba osoba, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "osoba_form";
        }

        osobaService.saveOsoba(osoba);
        model.addAttribute("saveSuccess", true);
        model.addAttribute("osoba", new Osoba());
        return "osoba_form";
    }

    @PostMapping("/updateOsobaForm/{id}")
    public String updateOsoba(@Valid Osoba osoba, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "osoba_form";
        }

        osobaService.saveOsoba(osoba);
        model.addAttribute("updateSuccess", true);
        return "osoba_form";
    }

    @GetMapping("/updateOsobaForm/{id}")
    public String updateOsobaForm(@PathVariable(value = "id") Long id, Model model) {
        Osoba osoba = osobaService.getOsobaById(id);
        model.addAttribute("osoba", osoba);
        return "osoba_form";
    }

    @GetMapping("/deleteOsoba/{id}")
    public String deleteOsoba(@PathVariable(value = "id") Long id,
                              @ModelAttribute("osobaPretraga") OsobaPretraga osobaPretraga) {
        osobaService.deleteOsobaById(id);
        return "redirect:" + osobaPretraga.getPath();
    }

    @GetMapping("/osobaDetails/{id}")
    public String osobaDetails(@PathVariable(value = "id") Long id, Model model) {
        Osoba osoba = osobaService.getOsobaById(id);
        model.addAttribute("osoba", osoba);
        return "osoba_details";
    }

}
