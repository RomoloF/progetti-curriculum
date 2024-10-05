package it.resina.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.resina.controller.servizzi.CalcolatoreService;

@Controller
public class CalcolatoreController {

    @Autowired
    private CalcolatoreService calcolatoreService;
    
    @GetMapping("/")
    public String home() {
        return "calcolatore";
    }
    @GetMapping("/calcolatore")
    public String mostraCalcolatore() {
        return "calcolatore";
    }

    @PostMapping("/calcola")
    public String calcolaResina(
            @RequestParam("lunghezza") double lunghezza,
            @RequestParam("larghezza") double larghezza,
            @RequestParam("altezza") double altezza, // Altezza in millimetri
            @RequestParam("rapporto") String rapporto,
            Model model) {

        String[] parts = rapporto.split(":");
        double[] risultato = calcolatoreService.calcolaResina(
                lunghezza, larghezza, altezza, Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));

        model.addAttribute("baseML", risultato[0]);
        model.addAttribute("baseGrammi", risultato[1]);
        model.addAttribute("indurenteML", risultato[2]);
        model.addAttribute("indurenteGrammi", risultato[3]);
        model.addAttribute("totaleML", risultato[4]);
        model.addAttribute("totaleGrammi", risultato[5]);

        return "risultato";
    }
}
