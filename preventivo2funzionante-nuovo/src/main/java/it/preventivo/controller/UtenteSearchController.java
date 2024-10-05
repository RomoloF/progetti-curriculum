package it.preventivo.controller;

import it.preventivo.entity.Utente;
import it.preventivo.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UtenteSearchController {

    @Autowired
    private UtenteService utenteService;

    @GetMapping("/searchUtente")
    public String searchUtente(@RequestParam(required = false) Long id,
                               @RequestParam(required = false) String email,
                               Model model) {
        if (id != null) {
            Utente utente = utenteService.findById(id).orElse(null);
            model.addAttribute("utente", utente);
        } else if (email != null && !email.isEmpty()) {
            Utente utente = utenteService.findByEmail(email).orElse(null);
            model.addAttribute("utente", utente);
        }
        return "utenteSearch";
    }
}

