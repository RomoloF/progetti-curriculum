package it.preventivo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.preventivo.entity.LavoriRestauro;
import it.preventivo.entity.LavoriTecnologici;
import it.preventivo.entity.Utente;
import it.preventivo.service.LavoriTecnologiciService;
import it.preventivo.service.UtenteService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/lavoriTecnologici")
public class LavoriTecnologiciController {

    @Autowired
    private LavoriTecnologiciService lavoriTecnologiciService;
    @Autowired
    private UtenteService utenteService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("lavoriTecnologiciList", lavoriTecnologiciService.findAll());
        
        List<Utente> utenti = utenteService.findAll();
        model.addAttribute("utente", new Utente());  // Assicurati che l'oggetto 'utente' sia presente nel modello
        return "lavoriTecnologici/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("lavoro", new LavoriTecnologici());
        return "lavoriTecnologici/form";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute  LavoriTecnologici lavoriTecnologici, Model model) {
    	System.out.println("Sto dentro il metodo /new di tipo Post");
        System.out.println("Salvo il Lavoro :"+lavoriTecnologici);  // Utilizzo System.out.println per stampare il lavoro salvato
        lavoriTecnologiciService.save(lavoriTecnologici);
        return "/lavoriTecnologici/list"; // Redirezione corretta
    }
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
    	System.out.println("Sto dentro al metodo /edit/{id} di tipo Get");
    	System.out.println("Stampo l'Id del Lavoro :"+id);
    	System.out.println("Stampo il Lavoro da modificare :"+lavoriTecnologiciService.findById(id));	
    	
        model.addAttribute("lavoro", lavoriTecnologiciService.findById(id));
        return "lavoriTecnologici/form";
    }

    @PostMapping("/edit/{id}")
    public String edit(@ModelAttribute LavoriTecnologici lavoriTecnologici) {
    	LavoriTecnologici lavoro = lavoriTecnologiciService.findById(lavoriTecnologici.getId());
        lavoriTecnologiciService.save(lavoriTecnologici);
        System.out.println("Stampo il Lavoro da modificare :"+lavoriTecnologici);
        return "redirect:/lavoriTecnologici/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        lavoriTecnologiciService.deleteById(id);
        return "redirect:/lavoriTecnologici/list";
    }
}

