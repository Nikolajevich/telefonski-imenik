package com.dev.nikola.telefonski_imenik.controllers;

import com.dev.nikola.telefonski_imenik.models.Adresa;
import com.dev.nikola.telefonski_imenik.models.Osoba;
import com.dev.nikola.telefonski_imenik.services.OsobaService;
import com.dev.nikola.telefonski_imenik.wrapper.OsobaAdresaForm;
import com.dev.nikola.telefonski_imenik.wrapper.OsobaPretraga;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@Controller
@SessionAttributes("osobaPretraga")
public class OsobaController {

    private final OsobaService osobaService;

    public OsobaController(OsobaService osobaService) {
        this.osobaService = osobaService;
    }

    @ModelAttribute("osobaPretraga")
    public OsobaPretraga initOsobaPretraga() {
        return new OsobaPretraga();
    }

    public String homePage(OsobaPretraga osobaPretraga, int pageNum, Model model) {
        int pageSize = 4;

        Page<Osoba> page = osobaService.getPaginatedParams(pageNum,
                pageSize,
                osobaPretraga.getOib(),
                osobaPretraga.getIme(),
                osobaPretraga.getPrezime(),
                osobaPretraga.getBroj(),
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
        return homePage(osobaPretraga, 1, model);
    }

    @GetMapping("/sort{pageNum}")
    public String sortHomePage(@PathVariable(value = "pageNum") int pageNum,
                               @RequestParam("sortField") String sortField,
                               @RequestParam("sortDir") String sortDir,
                               @ModelAttribute("osobaPretraga") OsobaPretraga osobaPretraga,
                               Model model) {
        osobaPretraga.setSortField(sortField);
        osobaPretraga.setSortDir(sortDir);
        return homePage(osobaPretraga, pageNum, model);
    }

    @GetMapping("/page/{pageNum}")
    public String paginateHomePage(@PathVariable(value = "pageNum") int pageNum,
                                         @ModelAttribute("osobaPretraga") OsobaPretraga osobaPretraga,
                                         Model model) {
        osobaPretraga.setPath("/page/" + pageNum);
        return homePage(osobaPretraga, pageNum, model);
    }

    @GetMapping("/novaOsobaForm")
    public String novaOsobaForm(Model model) {
        Osoba newOsoba = new Osoba();
        Adresa newAdresa = new Adresa();
        OsobaAdresaForm osobaAdresaForm = new OsobaAdresaForm();
        osobaAdresaForm.addAdresa(newAdresa);
        osobaAdresaForm.setOsoba(newOsoba);
        model.addAttribute("osobaAdresaForm", osobaAdresaForm);
        return "new_osoba_form";
    }

    @PostMapping("/novaOsobaForm")
    public String saveOsoba(@Valid OsobaAdresaForm osobaAdresaForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "new_osoba_form";
        }
        Osoba osoba = osobaAdresaForm.getOsoba();
        osoba.setAdresaSet(new HashSet<>(osobaAdresaForm.getAdresaList()));
        osobaService.saveOsoba(osoba);
        model.addAttribute("saveSuccess", true);
        Osoba newOsoba = new Osoba();
        Adresa newAdresa = new Adresa();
        osobaAdresaForm = new OsobaAdresaForm();
        osobaAdresaForm.addAdresa(newAdresa);
        osobaAdresaForm.setOsoba(newOsoba);
        model.addAttribute("osobaAdresaForm", osobaAdresaForm);
        return "new_osoba_form";
    }

    @PostMapping("/updateOsobaForm/{id}")
    public String updateOsoba(@Valid Osoba formOsoba, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "update_osoba_form";
        }

        Osoba osoba = osobaService.getOsobaById(formOsoba.getId());
        osoba.setIme(formOsoba.getIme());
        osoba.setPrezime(formOsoba.getPrezime());
        osoba.setBroj(formOsoba.getBroj());
        osoba.setOib(formOsoba.getOib());
        osobaService.saveOsoba(osoba);
        model.addAttribute("updateSuccess", true);
        return "update_osoba_form";
    }

    @GetMapping("/updateOsobaForm/{id}")
    public String updateOsobaForm(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("osoba", osobaService.getOsobaById(id));
        return "update_osoba_form";
    }

    @GetMapping("/deleteOsoba/{id}")
    public String deleteOsoba(@PathVariable(value = "id") Long id,
                              @SessionAttribute("osobaPretraga") OsobaPretraga osobaPretraga) {
        osobaService.deleteOsobaById(id);
        return "redirect:" + osobaPretraga.getPath();
    }

    @GetMapping("/osobaDetails/{id}")
    public String osobaDetails(@SessionAttribute("osobaPretraga") OsobaPretraga osobaPretraga, @PathVariable(value = "id") Long id, Model model) {
        osobaPretraga.setId(id);
        model.addAttribute("osobaPretraga", osobaPretraga);
        return "redirect:/osobaDetails";
    }

}
