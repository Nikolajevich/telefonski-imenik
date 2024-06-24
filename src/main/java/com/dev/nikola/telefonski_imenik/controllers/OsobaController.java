package com.dev.nikola.telefonski_imenik.controllers;

import com.dev.nikola.telefonski_imenik.models.Osoba;
import com.dev.nikola.telefonski_imenik.wrapper.OsobaPretraga;
import com.dev.nikola.telefonski_imenik.services.OsobaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class OsobaController {

    private final OsobaService osobaService;

    private OsobaPretraga osobaPretraga;
    private String path;
    private String sortField;
    private String sortDir;

    public OsobaController(OsobaService osobaService) {
        this.osobaService = osobaService;
        this.osobaPretraga = new OsobaPretraga();
    }

    public String paginatedHomePage(int pageNum, Model model) {
        int pageSize = 4;

        Page<Osoba> page = this.osobaService.getPaginatedParams(pageNum,
                                                                pageSize,
                                                                this.osobaPretraga.getOib(),
                                                                this.osobaPretraga.getIme(),
                                                                this.osobaPretraga.getPrezime(),
                                                                this.osobaPretraga.getGrad(),
                                                                this.osobaPretraga.getBroj(),
                                                                this.sortField,
                                                                this.sortDir);

        List<Osoba> listOsobe = page.getContent();

        model.addAttribute("osobaPretraga", this.osobaPretraga);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listOsobe", listOsobe);
        return "index";
    }

    @GetMapping("/")
    public String startHomePage(Model model) {
        this.sortField = "prezime";
        this.sortDir = "asc";
        this.path = "/";
        return paginatedHomePage(1, model);
    }

    @PostMapping({"/", "/sort{pageNum}", "/page/{pageNum}"})
    public String searchHomePage(@ModelAttribute("osobaPretraga") OsobaPretraga osobaPretraga, Model model) {
        this.osobaPretraga = osobaPretraga;
        this.path = "/";
        return paginatedHomePage(1, model);
    }

    @GetMapping("/sort{pageNum}")
    public String sortHomePage(@PathVariable(value = "pageNum") int pageNum,
                               @RequestParam("sortField") String sortField,
                               @RequestParam("sortDir") String sortDir,
                               Model model) {
        this.sortField = sortField;
        this.sortDir = sortDir;
        return paginatedHomePage(pageNum, model);
    }

    @GetMapping("/page/{pageNum}")
    public String startPaginatedHomePage(@PathVariable(value = "pageNum") int pageNum, Model model) {
        this.path = "/page/" + pageNum;
        return paginatedHomePage(pageNum, model);
    }

    @GetMapping("/novaOsobaForm")
    public String novaOsobaForm(Model model) {
        Osoba osoba = new Osoba();
        model.addAttribute("osoba", osoba);
        return "osoba_podaci";
    }

    @PostMapping("/novaOsobaForm")
    public String saveOsoba(@Valid Osoba osoba, BindingResult result) {
        if (result.hasErrors()) {
            return "osoba_podaci";
        }
        this.osobaService.saveOsoba(osoba);
        return "redirect:/";
    }

    @GetMapping("/updateOsobaForm/{id}")
    public String updateOsobaForm(@PathVariable( value = "id") Long id, Model model) {
        Osoba osoba = this.osobaService.getOsobaById(id);
        model.addAttribute("osoba", osoba);
        return "osoba_podaci";
    }

    @GetMapping("/deleteOsoba/{id}")
    public String deleteOsoba(@PathVariable( value = "id") Long id) {
        this.osobaService.deleteOsobaById(id);
        return "redirect:" + this.path;
    }

}
