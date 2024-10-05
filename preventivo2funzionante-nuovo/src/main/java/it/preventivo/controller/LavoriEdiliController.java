package it.preventivo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.preventivo.entity.LavoriEdili;
import it.preventivo.entity.Utente;
import it.preventivo.service.LavoriEdiliService;
import it.preventivo.service.UtenteService;

@Controller
@RequestMapping("/lavori-edili")
public class LavoriEdiliController {

    @Autowired
    private LavoriEdiliService lavoriEdiliService;
    @Autowired
    private UtenteService utenteService;
    
    @GetMapping
    public String mostraTutti(Model model) {
        List<LavoriEdili> lavoriEdili = lavoriEdiliService.findAll();
        model.addAttribute("lavori", lavoriEdili);
        List<Utente> utenti = utenteService.findAll();
        model.addAttribute("utenti", utenti);
        model.addAttribute("utente", new Utente());  
        return "lavori_edili";
    }

    @GetMapping("/nuovo")
    public String mostraFormNuovo(Model model) {
        model.addAttribute("lavoro", new LavoriEdili());
        return "form_lavoro";
    }

    @PostMapping("/nuovo")
    public String salvaNuovo(@ModelAttribute("lavoro") LavoriEdili lavoro, RedirectAttributes redirectAttributes) {
        lavoriEdiliService.save(lavoro);
        redirectAttributes.addFlashAttribute("successMessage", "Lavoro edile CREATO con successo!");
        return "redirect:/lavori-edili";
    }

    @GetMapping("/{id}/modifica")
    public String mostraFormModifica(@PathVariable("id") long id, Model model) {
        LavoriEdili lavoro = lavoriEdiliService.findById(id);
        if (lavoro == null) {
            return "redirect:/lavori-edili";
        }
        model.addAttribute("lavoro", lavoro);
        return "form_lavoro";
    }

    @PostMapping("/{id}/modifica")
    public String salvaModifiche(@PathVariable("id") long id, @ModelAttribute("lavoro") LavoriEdili lavoro, RedirectAttributes redirectAttributes) {
        lavoro.setId(id);
        lavoriEdiliService.save(lavoro);
        redirectAttributes.addFlashAttribute("successMessage", "Lavoro edile MODIFICATO con successo!");
        return "redirect:/lavori-edili";
    }

    @GetMapping("/{id}/elimina")
    public String elimina(@PathVariable("id") long id,RedirectAttributes redirectAttributes ) {
        lavoriEdiliService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Lavoro edile ELIMINATO con successo!");
        
        return "redirect:/lavori-edili";
    }
}

