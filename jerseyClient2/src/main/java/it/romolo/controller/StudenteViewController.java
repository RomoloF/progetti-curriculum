package it.romolo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import it.romolo.model.Studente;
import it.romolo.service.ClasseService;
import it.romolo.service.StudenteService;

@Controller
@RequestMapping("/studenti")
public class StudenteViewController {

	private Logger logger=LoggerFactory.getLogger(StudenteViewController.class);

    @Autowired
    private StudenteService studenteService;
    @Autowired
    private ClasseService classeService;

    @GetMapping
    public String listStudenti(Model model) {
        List<Studente> studenti = studenteService.getAllStudenti(); // Implementa il metodo getAllStudenti nel servizio
        List<Classe>  classi=  classeService.getAllClassi(); // Implementa il metodo getAllStudenti nel servizio

        model.addAttribute("classi" , classi);
        model.addAttribute("studenti", studenti);
        return "studenti";
    }

    @GetMapping("/{id}")
    public String getStudente(@PathVariable int id, Model model) {
        Studente studente = studenteService.getStudenteById(id);
        model.addAttribute("studente", studente);
        return "studente";
    }

 // Metodo per cercare studenti per nome
    @GetMapping("/cercaNome/{nome}")
    public String getStudentiByNome(@RequestParam String nome, Model model) {
    	System.out.println("Sono dentro allo StudenteViewService metodo getStudentiByNome");
    	List<Studente> studenti = studenteService.cercaPerNome(nome); // Implementa il metodo getAllStudenti nel servizio
        model.addAttribute("studenti", studenti);
        return "studenti";
    }





     @GetMapping("/nuovo") // Modifica a @GetMapping
    public String createStudente(Model model) {
    	model.addAttribute("studente", new Studente()); // Passa un nuovo oggetto Studente al modello
    	model.addAttribute("classi", classeService.getAllClassi()); // Aggiungi la lista delle classi al modello
        return "newStudente"; // Ritorna il nome della vista
    }

    @PostMapping("/nuovo")
    public String createStudente(@ModelAttribute Studente studente) {
      studenteService.createStudente(studente);
      // Opzionale: reindirizzare a pagina di conferma o lista studenti
      return "redirect:/studenti"; // Modifica in base alla tua pagina di visualizzazione
    }





    @GetMapping("/studentiRicercaNomeCognomeEmail")
    public String getStudentiRicercaNomeCognomeEmail   (Model model) {
    	List<Classe>  classi=  classeService.getAllClassi(); // Implementa il metodo getAllStudenti nel servizio
    	model.addAttribute("classi" , classi);
    	return "studentiRicercaNomeCognomeEmail"; //Va alla vista di tipo form di nome(studentiRicercaNomeCognomeEmail.html)
    }




//    @GetMapping("/cercaPerNome/{nome}")
//    public String cercPerNome   (@PathVariable String nome ,Model model) {
//    	System.out.println("nome è:"+nome);
//    	List<Studente>  studenti=  studenteService.cercaPerNome(nome);
//    	model.addAttribute("studenti" , studenti);
//    	return "studenti";
//    }


    @GetMapping("/cercaPerNome")
    public String cercPerNome(@RequestParam ( "nomeStudente") String nome , Model model) {
    	System.out.println("Sto dentro a StudenteViewController il nome è:"+nome+" Il Model è: "+model);
    	System.out.println("nome è:"+nome+" Ciao Da Romolo Fiorenza");
    	List<Studente>  studenti=  studenteService.cercaPerNome(nome); // La tua lista originale di studenti

    	List<Studente> studentiNome = studenti.stream()
    		    .filter(studente -> studente.getNome().equals(nome))
    		    .collect(Collectors.toList());

    	model.addAttribute("studenti" , studentiNome);
    	return "studenti";


    }
    @GetMapping("/cercaPercogome")
    public String cercPercognomeome(@RequestParam ( "nomeStudente") String cognome , Model model) {
    	System.out.println("Sto dentro a StudenteViewController il cognome è:"+cognome+" Il Model è: "+model);
    	System.out.println("nome è:"+cognome+" Ciao Da Romolo Fiorenza");
    	List<Studente>  studenti=  studenteService.cercaPerCognome(cognome); // La tua lista originale di studenti

    	List<Studente> studenticognome = studenti.stream()
    		    .filter(studente -> studente.getNome().equals(cognome))
    		    .collect(Collectors.toList());

    	model.addAttribute("studenti" , studenticognome);
    	return "studenti";


    }




    @GetMapping("/listaperclasse/{idClasse}")
    public String visualizzaStudentiPerClasse(@PathVariable int idClasse, Model model) {
        List<Studente> studentiPerClasse = studenteService.cercaPerClasse(idClasse);
        model.addAttribute("studenti", studentiPerClasse);
        return "listaStudentiClasse"; // Assicurati di avere una vista con questo nome
    }

	///////////////////////////////////////////////////////////////////////
								// MODIFICA Studente metodo GET CHE POI VA ALLA VISTA MODIFICACLASSE.HTML
								/**
								* Il metodo @GetMapping("/modifica/{id}") carica lo studente esistente dal
								* servizio e la aggiunge al modello per essere visualizzata nel form di
								* modifica.
								*
								* @param id
								* @param model
								* @return
								*/
								@GetMapping("/modifica/{id}")
								public String editStudente(@PathVariable int id, Model model) {
								Studente studente = studenteService.getStudenteById(id);

								System.out.println("***********************************************************************************");
								System.out.println("Sono entrato nel metodo editStudente di tipo  Get Questo è il StudenteViewController");
								System.out.println("Lo studente trovato nel DB è >>>"+studente);
								System.out.println("************************************************************************************");


								if (studente == null) {
								return "erroreStudente"; // Modifica in base alla tua pagina di errore
								}

								model.addAttribute("classi", classeService.getAllClassi()); // Aggiungi la lista delle classi al modello
								model.addAttribute("studente", studente);

								System.out.println("************************************************************************************");
								System.out.println("O asegnato al model classi >>>"+classeService.getAllClassi());
								System.out.println("************************************************************************************");
								System.out.println("O asegnato al model studente >>>"+studenteService.getStudenteById(id));
								System.out.println("************************************************************************************");

								return "modificaStudente"; // Nome della tua vista per modificare una classe
								}

								 @PostMapping("/modifica/{id}")
								    public String editStudente(@PathVariable int id, @ModelAttribute Studente studente) {
								        studenteService.updateStudente(id, studente);
								        return "redirect:/studenti"; // Reindirizza alla lista di studenti dopo l'aggiornamento
								    }
                            ////////////////////////////////////////////////////////////////////////
//							/**
//							* Il metodo @PostMapping("/modifica/{id}") riceve l'ID della classe e l'oggetto
//							* Studente aggiornato dal form. Recupera lo studente esistente dal servizio,
//							* aggiorna i suoi campi con i valori del form e quindi chiama il servizio per
//							* salvare le modifiche.
//							*
//							* @param id
//							* @param classe
//							* @return
//							*/
//							@PostMapping("/modifica/{id}")
//							/**
//							* Il metodo editClasse utilizza un'istanza della classe esistente per mantenere
//							* l'ID e aggiornare i campi necessari, riducendo il rischio di perdere dati
//							* importanti che non sono stati inclusi nel form.
//							*
//							* @param id
//							* @param classe
//							* @return
//							*/
//							public String editStudente(@PathVariable int id, @ModelAttribute Studente studente) {
//							System.out.println("Sono entrato nel metodo POST. E lo studente è:" + studente + "  l ' ID:" + id);
//
//							System.out.println("***********************************************************************************");
//							System.out.println("Sono entrato nel metodo editStudente di tipo  POST Questo è il StudenteViewController");
//
//							System.out.println("Lo studente ricevuto è >>>"+studente+" e l'ID è >>"+id);
//							System.out.println("************************************************************************************");
//
//
//
//
//							Studente aggiornaStudente = studenteService.getStudenteById(id);
//
//							if (aggiornaStudente == null) {
//							return "erroreStudente"; // Modifica in base alla tua pagina di errore
//							}
//
//							System.out.println("studente.nome:"+studente.getNome());
//							System.out.println("studente:"+studente.toString());
//
//
//							aggiornaStudente.setId(studente.getId());
//							aggiornaStudente.setClasse(studente.getClasse());
//							aggiornaStudente.setNome(studente.getNome());
//							aggiornaStudente.setCognome(studente.getCognome());
//							aggiornaStudente.setRifGenitore(studente.getRifGenitore());
//							aggiornaStudente.setTelefono(studente.getTelefono());
//							aggiornaStudente.setEmail(studente.getEmail());
//
//							System.out.println("Lo studente aggiornato è >>>> "+aggiornaStudente);
//
//							studenteService.updateStudente(aggiornaStudente);
//
//							System.out.println("La classe  è aggiornata con sucesso ........");
//
//							return "redirect:/"; // Reindirizza alla lista di classi e studenti dopo l'aggiornamento
//							}





//							@PostMapping("/modifica/{id}")
//							public String editStudente(@PathVariable Integer id, @ModelAttribute Studente studente) {
//							    logger.info("lo studente è: {} l ' ID: {}", studente, id);
//
//							    Studente aggiornaStudente = studenteService.getStudenteById(id);
//							    if (aggiornaStudente == null) {
//							        return "erroreStudente"; // Modifica in base alla tua pagina di errore
//							    }
//							    System.out.println("AIUTO ===============================================");
//							    System.out.println("AIUTO ===============================================");
//							    System.out.println("AIUTO ===============================================");
//
//							    System.out.println("Lo studente è >>>"+studente+" e l'ID è >>"+id);
//
//							    System.out.println("AIUTO ===============================================");
//							    System.out.println("AIUTO ===============================================");
//							    System.out.println("AIUTO ===============================================");
//
//							    // Aggiorna i campi dello studente
//							    aggiornaStudente.setId(studente.getId());
//							    aggiornaStudente.setNome(studente.getNome());
//							    aggiornaStudente.setCognome(studente.getCognome());
//							    aggiornaStudente.setClasse(studente.getClasse());
//							    aggiornaStudente.setTelefono(studente.getTelefono());
//							    aggiornaStudente.setEmail(studente.getEmail());
//							    aggiornaStudente.setRifGenitore(studente.getRifGenitore());
//
//							    logger.info("Lo studente aggiornato è: {}", aggiornaStudente);
//							    studenteService.updateStudente(aggiornaStudente);
//							    logger.info("Lo studente è stato aggiornato con successo.");
//
//							    return "redirect:/"; // Reindirizza alla lista di studenti dopo l'aggiornamento
//							}
//
//
//
//



    /**
     * Elimina studente riceve solo l' id dal chiamante
     * @param id
     * @return
     */
    @GetMapping("/elimina/{id}")
    public String deleteStudente(@PathVariable ("id") Integer id) {
    	logger.info("Stai cancellando lo studente con id:{}",id);
        studenteService.deleteStudente(id);
        logger.info("Hai cancellato lo studente con id:{}",id);
        return "redirect:/"; // Reindirizza alla lista di classi dopo la cancellazione
    }
    /////////////////////////////////////////////////////////////////////////////////////////////




}



