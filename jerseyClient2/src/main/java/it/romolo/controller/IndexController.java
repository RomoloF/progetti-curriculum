package it.romolo.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.romolo.model.Classe;
import it.romolo.model.Studente;
import it.romolo.service.ClasseService;
import it.romolo.service.StudenteService;

@Controller
public class IndexController {

    @Autowired
    private ClasseService classeService;

    @Autowired
    private StudenteService studenteService;

    @GetMapping("/")
    public String showIndex(Model model) {
        List<Classe> classi = classeService.getAllClassi();
        List<Studente> studenti = studenteService.getAllStudenti();
        model.addAttribute("classi", classi);
        model.addAttribute("studenti", studenti);
        return "index"; // Nome della tua vista index.html
    }

    @GetMapping("/vistaListaClassi")
    public String vistaListaClassi(Model model) {
        List<Classe> classi = classeService.getAllClassi();
        List<Studente> studenti = studenteService.getAllStudenti();
        model.addAttribute("classi", classi);
        model.addAttribute("studenti", studenti);
        return "classi"; // Nome della tua vista index.html
    }

    @GetMapping("/contatti")
    public String contatti() {
        return "contatti"; // Nome della tua vista contatti.html
    }

    @GetMapping("/chiSiamo")
    public String chiSiamo() {
        return "chiSiamo"; // Nome della tua vista chiSiamo.html
    }
}
