package com.dev.nikola.telefonski_imenik.controllers;

import com.dev.nikola.telefonski_imenik.models.Osoba;
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
@SessionAttributes({"osobaPretraga", "sortField", "sortDir", "path"})
public class OsobaController {

    private final OsobaService osobaService;

    public OsobaController(OsobaService osobaService) {
        this.osobaService = osobaService;
    }

    @ModelAttribute("osobaPretraga")
    public OsobaPretraga initOsobaPretraga() {
        return new OsobaPretraga();
    }

    @ModelAttribute("sortField")
    public String initSortField() {
        return "prezime";
    }

    @ModelAttribute("sortDir")
    public String initSortDir() {
        return "asc";
    }

    @ModelAttribute("sortField")
    public String initPath() {
        return "/";
    }

    public String paginatedHomePage(OsobaPretraga osobaPretraga,
                                    String sortField,
                                    String sortDir,
                                    int pageNum,
                                    Model model) {
        int pageSize = 4;

        Page<Osoba> page = osobaService.getPaginatedParams(pageNum,
                pageSize,
                osobaPretraga.getOib(),
                osobaPretraga.getIme(),
                osobaPretraga.getPrezime(),
                osobaPretraga.getGrad(),
                osobaPretraga.getBroj(),
                sortField,
                sortDir);

        List<Osoba> listOsobe = page.getContent();

        model.addAttribute("osobaPretraga", osobaPretraga);

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listOsobe", listOsobe);
        return "index";
    }

    @RequestMapping({"/", "/sort{pageNum}", "/page/{pageNum}"})
    public String setupHomePage(@ModelAttribute("osobaPretraga") OsobaPretraga osobaPretraga,
                                @ModelAttribute("sortField") String sortField,
                                @ModelAttribute("sortDir") String sortDir,
                                Model model) {
        model.addAttribute("path", "/");
        return paginatedHomePage(osobaPretraga, sortField, sortDir, 1, model);
    }

    @GetMapping("/sort{pageNum}")
    public String sortHomePage(@PathVariable(value = "pageNum") int pageNum,
                               @RequestParam("sortField") String sortField,
                               @RequestParam("sortDir") String sortDir,
                               @ModelAttribute("osobaPretraga") OsobaPretraga osobaPretraga,
                               Model model) {
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        return paginatedHomePage(osobaPretraga, sortField, sortDir, pageNum, model);
    }

    @GetMapping("/page/{pageNum}")
    public String startPaginatedHomePage(@PathVariable(value = "pageNum") int pageNum,
                                         @RequestParam("sortField") String sortField,
                                         @RequestParam("sortDir") String sortDir,
                                         @ModelAttribute("osobaPretraga") OsobaPretraga osobaPretraga,
                                         Model model) {
        model.addAttribute("path", "/page/" + pageNum);
        return paginatedHomePage(osobaPretraga, sortField, sortDir, pageNum, model);
    }

    @GetMapping("/novaOsobaForm")
    public String novaOsobaForm(Model model) {
        model.addAttribute("osoba", new Osoba());
        return "osoba_podaci";
    }

    @PostMapping("/novaOsobaForm")
    public String saveOsoba(@Valid Osoba osoba, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "osoba_podaci";
        }

        osobaService.saveOsoba(osoba);
        model.addAttribute("saveSuccess", true);
        model.addAttribute("osoba", new Osoba());
        return "osoba_podaci";
    }

    @PostMapping("/updateOsobaForm/{id}")
    public String updateOsoba(@Valid Osoba osoba, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "osoba_podaci";
        }

        osobaService.saveOsoba(osoba);
        model.addAttribute("updateSuccess", true);
        return "osoba_podaci";
    }

    @GetMapping("/updateOsobaForm/{id}")
    public String updateOsobaForm(@PathVariable(value = "id") Long id, Model model) {
        Osoba osoba = osobaService.getOsobaById(id);
        model.addAttribute("osoba", osoba);
        return "osoba_podaci";
    }

    @GetMapping("/deleteOsoba/{id}")
    public String deleteOsoba(@PathVariable(value = "id") Long id,
                              @ModelAttribute("path") String path) {
        osobaService.deleteOsobaById(id);
        return "redirect:" + path;
    }

}
