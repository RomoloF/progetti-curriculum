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

import it.preventivo.entity.LavoriRestauro;
import it.preventivo.entity.Utente;
import it.preventivo.service.LavoriRestauroService;
import it.preventivo.service.UtenteService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/lavoriRestauro")
public class LavoriRestauroController {

    @Autowired
    private LavoriRestauroService lavoriRestauroService;
    @Autowired
    private UtenteService utenteService;

    @GetMapping
    public String listLavoriRestauro(Model model) {
        List<LavoriRestauro> lavoriRestauro = lavoriRestauroService.findAll();
        model.addAttribute("lavoriRestauro", lavoriRestauro);
        List<Utente> utenti = utenteService.findAll();
        model.addAttribute("utente", new Utente());
        return "lavoriRestauro/list";
    }
    @GetMapping("/new")
    public String createForm(Model model) {
    	System.out.println("Sto dentro il metodo /new di tipo Get");
        model.addAttribute("lavoro", new LavoriRestauro());
        return "lavoriRestauro/form";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute LavoriRestauro lavoriRestauro, Model model) {
    	System.out.println("Sto dentro il metodo /new di tipo Post");
        lavoriRestauroService.save(lavoriRestauro);
        List<LavoriRestauro> lavoriRestauro1 = lavoriRestauroService.findAll();
        model.addAttribute("lavoriRestauro", lavoriRestauro1);
        System.out.println("Ho salvato nel db ................");
        //redirectAttributes.addFlashAttribute("successMessage", "Lavoro creato con successo!");
        return "/lavoriRestauro/list";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
    	System.out.println("Sto dentro il metodo /edit/{id} di tipo Get");
    	LavoriRestauro lavoro = lavoriRestauroService.findById(id);
        model.addAttribute("lavoriRestauro", lavoriRestauroService.findById(id));
        
        model.addAttribute("lavoro", lavoro);
        System.out.println("Stampo l'Id del Lavoro :"+id);
        System.out.println("Stampo il Lavoro da modificare :"+lavoro);
        return "lavoriRestauro/form";
    }

    @PostMapping("/edit/{id}")
    public String edit(@ModelAttribute LavoriRestauro lavoriRestauro) {
    	LavoriRestauro lavoro = lavoriRestauroService.findById(lavoriRestauro.getId());
    	System.out.println("Sto dentro il metodo /edit/{id} di tipo Post");
    	System.out.println("Stampo l'Id del Lavoro"+lavoriRestauro.getId());
        System.out.println("Stampo il Lavoro da modificare :"+lavoro);
        lavoriRestauroService.save(lavoriRestauro);
        return "/lavoriRestauro/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        lavoriRestauroService.deleteById(id);
        return "/lavoriRestauro/list";
    }
}

