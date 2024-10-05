package it.preventivo.controller;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import it.preventivo.entity.LavoriManutenzione;
import it.preventivo.entity.Utente;
import it.preventivo.service.LavoriManutenzioneServiceImpl;
import it.preventivo.service.UtenteService;
/**
 * Controller per gestire le operazioni su LavoriManutenzione.
 */
@Controller
@RequestMapping("/lavoriManutenzione")
public class LavoriManutenzioneController {

    @Autowired
    private LavoriManutenzioneServiceImpl lavoriManutenzioneService;
    @Autowired
    private UtenteService utenteService;
    
    /**
     * Restituisce tutti i lavori di manutenzione.
     * @return lista di LavoriManutenzione
     */
    @GetMapping
    public String getAll(Model model) {
        List<LavoriManutenzione> lavoriManutenzioneList = lavoriManutenzioneService.findAll();
        model.addAttribute("lavoriManutenzioneList", lavoriManutenzioneList);        
        List<Utente> utenti = utenteService.findAll();
        model.addAttribute("utente", new Utente());  // Assicurati che l'oggetto 'utente' sia presente nel modello        
        return "lavoriManutenzione/list";// Restituisce la vista "lista.html" in templates/lavoriManutenzione/
    }

    /**
     * Mostra il dettaglio di un lavoro di manutenzione.
     * @param id Identificativo del lavoro di manutenzione
     * @param model Modello per passare i dati alla vista
     * @return nome della vista
     */
    @GetMapping("/{id}")
    public String getLavoroById(@PathVariable("id") Long id, Model model) {
        LavoriManutenzione lavoro = lavoriManutenzioneService.findById(id);
        if (lavoro == null) {
            return "redirect:/lavoriManutenzione";
        }
        model.addAttribute("lavoro", lavoro);
        return "lavoriManutenzione/dettaglio"; // Restituisce la vista "dettaglio.html" in templates/lavori/
    }

    /**
     * Mostra il form per aggiungere un nuovo lavoro di manutenzione.
     * @param model Modello per passare i dati alla vista
     * @return nome della vista
     */
    @GetMapping("/nuovo")
    public String createForm(Model model) {    	
        model.addAttribute("lavoro", new LavoriManutenzione());
        return "lavoriManutenzione/form";// Restituisce la vista "form.html" in templates/lavoriManutenzione/
    }
    
    /**
     * Gestisce il salvataggio di un nuovo lavoro di manutenzione.
     * @param lavoro Lavoro di manutenzione da salvare
     * @return reindirizza alla lista dei lavori
     */
    @PostMapping("/salvaNuovo")
    public String create(@ModelAttribute("lavoro")  LavoriManutenzione lavoriManutenzione) {
        lavoriManutenzioneService.save(lavoriManutenzione);
        return "redirect:/lavoriManutenzione";
    }
       
    /**
     * Mostra il form per modificare un lavoro di manutenzione.
     * @param id Identificativo del lavoro di manutenzione
     * @param model Modello per passare i dati alla vista
     * @return nome della vista
     */
    @GetMapping("/modifica/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        LavoriManutenzione lavoro = lavoriManutenzioneService.findById(id);        
        if (lavoro == null) {
            return "redirect:/lavoriManutenzione"; // Se il lavoro non esiste, reindirizza alla lista
        }
        model.addAttribute("lavoro", lavoro);
        return "lavoriManutenzione/form"; // Utilizza la stessa vista "form.html" per aggiungere/modificare
    }

    @PostMapping("/modifica/{id}")
    public String updateLavoriManutenzione(@PathVariable("id") Long id, @ModelAttribute("lavoro") LavoriManutenzione lavoroDetails, RedirectAttributes redirectAttributes) {
        LavoriManutenzione lavoro = lavoriManutenzioneService.findById(id);
        if (lavoro == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lavoro non trovato!");
            return "redirect:/lavoriManutenzione";
        }
        lavoro.setCodice(lavoroDetails.getCodice());
        lavoro.setDescrizione(lavoroDetails.getDescrizione());
        lavoro.setPrezzo(lavoroDetails.getPrezzo());
        //lavoro.setQuantitaMisuraPezzi(lavoroDetails.getQuantitaMisuraPezzi());
        //lavoro.setSigla(lavoroDetails.getSigla());
        lavoriManutenzioneService.save(lavoro);
        redirectAttributes.addFlashAttribute("successMessage", "Lavoro aggiornato con successo!");
        return "redirect:/lavoriManutenzione";
    }
    
    
    
    @GetMapping("/elimina/{id}")
    public String deleteLavoriManutenzione(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
    	LavoriManutenzione lavoro = lavoriManutenzioneService.findById(id);
        if (lavoro == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lavoro non trovato!");
            return "redirect:/lavoriManutenzione";
        }
        lavoriManutenzioneService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Lavoro eliminato con successo!");
        return "redirect:/lavoriManutenzione";
    }
    
    
    @GetMapping("/cercaPerId")
    public String showSearchForm() {
        System.out.println("Sto dentro il metodo /cercaPerId di tipo Get");
        return "lavoriManutenzione/cercaPerId"; // Restituisce la vista "cercaPerId.html" in templates/lavoriManutenzione/
    }
    /**
     * Cerca un lavoro di manutenzione per ID e mostra il dettaglio.
     * @param id Identificativo del lavoro di manutenzione
     * @param model Modello per passare i dati alla vista
     * @return nome della vista
     */
    @PostMapping("/cercaPerId")
    public String searchLavoroById(@RequestParam("id") Long id, Model model) {
        System.out.println("Sto dentro il metodo /cercaPerId di tipo Post");
        LavoriManutenzione lavoro = lavoriManutenzioneService.findById(id);
        if (lavoro == null) {
            return "redirect:/lavoriManutenzione"; // Se il lavoro non esiste, reindirizza alla lista
        }
        model.addAttribute("lavoro", lavoro);
        return "lavoriManutenzione/datail"; // Restituisce la vista "dettaglio.html" in templates/lavoriManutenzione/
    }
    @GetMapping("/cercaPerDescrizione")
    public String showSearchFormDesc() {
        System.out.println("Sto dentro il metodo /cercaPerDescrizionedi tipo Get");
        return "lavoriManutenzione/cercaPerDescrizione"; // Restituisce la vista "cercaPerId.html" in templates/lavoriManutenzione/
    }
    @PostMapping("/cercaPerDescrizione")
    public String searchLavoriByDescrizione(@RequestParam("descrizione") String descrizione, Model model) {
    	 System.out.println("Sto dentro il metodo /cercaPerDescrizione di tipo Post");
        List<LavoriManutenzione> lavori = lavoriManutenzioneService.findByDescrizioneContaining(descrizione);
        model.addAttribute("lavoriManutenzioneList", lavori);  
       // model.addAttribute("lavori", lavori);
        return "lavoriManutenzione/list"; // Restituisce la vista "elenco.html" in templates/lavoriManutenzione/
    }

    
    
}


