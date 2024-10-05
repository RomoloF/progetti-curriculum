package it.romolo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.romolo.model.Classe;
import it.romolo.service.ClasseService;

@Controller
@RequestMapping("/classi")
public class ClasseController {

	@Autowired
	private ClasseService classeService;

	@GetMapping
	public String listClassi(Model model) {
		List<Classe> classi = classeService.getAllClassi();
		model.addAttribute("classi", classi);
		return "classi"; // Nome della tua vista per la lista di classi
	}

	@GetMapping("/{id}")
	public String getClasse(@PathVariable int id, Model model) {
		Classe classe = classeService.getClasseById(id);
		if (classe == null) { // Gestire classe non trovata (es. reindirizzare a pagina di errore)
			return "erroreClasse"; // Modifica in base alla tua pagina di errore
		}
		model.addAttribute("classe", classe);
		return "classe"; // Nome della tua vista per visualizzare i dettagli di una classe
	}

	// Metodo per cercare classi per anno scolastico
    @GetMapping("/searchByAnnoScolastico/{annoScolastico}")
    public String searchClassiByAnnoScolastico(@PathVariable String annoScolastico, Model model) {
        List<Classe> classi = classeService.searchClassiByAnnoScolastico(annoScolastico);
        model.addAttribute("classi", classi);
        return "classi"; // Ritorna alla vista delle classi con i risultati filtrati
    }

    // Metodo per cercare classi per nome
    @GetMapping("/searchByNome")
    public String searchClassiByNome(@RequestParam String nome, Model model) {
        List<Classe> classi = classeService.searchClassiByNome(nome);
        model.addAttribute("classi", classi);
        return "classi"; // Ritorna alla vista delle classi con i risultati filtrati
    }

 // Metodo per cercare classi per sezione
    @GetMapping("/searchBySezione")
    public String searchClassiBySezione(@RequestParam String sezione, Model model) {
        List<Classe> classi = classeService.searchClassiBySezione(sezione);
        model.addAttribute("classi", classi);
        return "classi"; // Ritorna alla vista delle classi con i risultati filtrati
    }

 // Metodo per cercare classi per numero dell'aula
    @GetMapping("/searchByAulaNum")
    public String searchClassiByAulaNum(@RequestParam String aulaNum, Model model) {
        List<Classe> classi = classeService.searchClassiByAulaNum(aulaNum);
        model.addAttribute("classi", classi);
        return "classi"; // Ritorna alla vista delle classi con i risultati filtrati
    }

 // Metodo per cercare classi per piano
    @GetMapping("/searchByPiano")
    public String searchClassiByPiano(@RequestParam String piano, Model model) {
        List<Classe> classi = classeService.searchClassiByPiano(piano);
        model.addAttribute("classi", classi);
        return "classi"; // Ritorna alla vista delle classi con i risultati filtrati
    }

	@GetMapping("/nuovo")
	public String createClasse(Model model) {
		model.addAttribute("classe", new Classe()); // Crea un nuovo oggetto Classe vuoto
		return "nuovoClasse"; // Nome della tua vista per la creazione di una nuova classe
	}

	@PostMapping("/nuovo")
	public String createClasse(@ModelAttribute Classe classe) {
		classeService.createClasse(classe);
		return "redirect:/classi"; // Reindirizza alla lista di classi dopo la creazione
	}

	///////////////////////////////////////////////////////////////////////
	// MODIFICA CLASSE METODO GET CHE POI VA ALLA VISTA MODIFICACLASSE.HTML
	/**
	 * Il metodo @GetMapping("/modifica/{id}") carica la classe esistente dal
	 * servizio e la aggiunge al modello per essere visualizzata nel form di
	 * modifica.
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/modifica/{id}")
	public String editClasse(@PathVariable int id, Model model) {
		Classe classe = classeService.getClasseById(id);
		if (classe == null) {
			return "erroreClasse"; // Modifica in base alla tua pagina di errore
		}

		model.addAttribute("classe", classe);
		return "modificaClasse"; // Nome della tua vista per modificare una classe
	}

	////////////////////////////////////////////////////////////////////////
	/**
	 * Il metodo @PostMapping("/modifica/{id}") riceve l'ID della classe e l'oggetto
	 * Classe aggiornato dal form. Recupera la classe esistente dal servizio,
	 * aggiorna i suoi campi con i valori del form e quindi chiama il servizio per
	 * salvare le modifiche.
	 *
	 * @param id
	 * @param classe
	 * @return
	 */
	@PostMapping("modifica/{id}")
	/**
	 * Il metodo editClasse utilizza un'istanza della classe esistente per mantenere
	 * l'ID e aggiornare i campi necessari, riducendo il rischio di perdere dati
	 * importanti che non sono stati inclusi nel form.
	 *
	 * @param id
	 * @param classe
	 * @return
	 */
	public String editClasse(@PathVariable int id, @ModelAttribute Classe classe) {
		System.out.println("la classe è:" + classe + "  l ' ID:" + id);

		Classe aggiornaClasse = classeService.getClasseById(id);

		if (aggiornaClasse == null) {
			return "erroreClasse"; // Modifica in base alla tua pagina di errore
		}

		System.out.println("classe.annoscolastico:" + classe.getAnnoScolastico());

		aggiornaClasse.setId(classe.getId());
		aggiornaClasse.setAnnoScolastico(classe.getAnnoScolastico());
		aggiornaClasse.setAulaNum(classe.getAulaNum());
		aggiornaClasse.setClasseAnno(classe.getClasseAnno());
		aggiornaClasse.setPiano(classe.getPiano());
		aggiornaClasse.setSezione(classe.getSezione());
		aggiornaClasse.setStudentes(classe.getStudentes());

		System.out.println("La classe aggiornate è >>>> " + aggiornaClasse);

		classeService.updateClasse(aggiornaClasse);

		System.out.println("La classe  è aggiornata con sucesso ........");

		return "redirect:/"; // Reindirizza alla lista di classi dopo l'aggiornamento
	}

	/**
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/elimina/{id}")
	public String deleteClasse(@PathVariable("id") Integer id) {
		classeService.deleteClasse(id);
		return "redirect:/classi"; // Reindirizza alla lista di classi dopo la cancellazione
	}
}
