package it.preventivo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import it.preventivo.entity.LavoriElettrici;
import it.preventivo.entity.LavoriManutenzione;
import it.preventivo.entity.LavoriSelezionati;
import it.preventivo.entity.LavoriEdili;
import it.preventivo.entity.LavoriTecnologici;
import it.preventivo.entity.LavoriRestauro;
import it.preventivo.service.LavoriEdiliService;
import it.preventivo.service.LavoriElettriciServiceImpl;
//import it.preventivo.service.LavoriManutenzioneService;
import it.preventivo.service.LavoriManutenzioneServiceImpl;
import it.preventivo.service.LavoriRestauroService;
import it.preventivo.service.LavoriTecnologiciService;
import it.preventivo.service.PreventivoServiceImpl;
import it.preventivo.service.UtenteService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

@Controller
@RequestMapping("/preventivo")
public class PreventivoController {

	public enum LavoroEdile {
	    LAVORI_EDILI("Lavori edili"),
	    LAVORI_ELETTRICI("Lavori elettrici"),
	    LAVORI_TECNOLOGICI("Lavori tecnologici"),
		LAVORI_MANUTENZIONE("Lavori manutenzione"),
		LAVORI_DI_RESTAURO("Lavori_di_restauro");
	    private final String descrizione;

	    LavoroEdile(String descrizione) {
	        this.descrizione = descrizione;
	    }

	    public String getDescrizione() {
	        return descrizione;
	    }
	}
	double somma ;
	@Autowired
	private LavoriSelezionati lavoroSelezionato;
	
	private List <LavoriSelezionati> lavoriSelezionatiList = new ArrayList<LavoriSelezionati>();
	
    @Autowired
    private UtenteService utenteService;
//    @Autowired
//    private LavorazioneRepository lavorazioneRepository;
    
    @Autowired
    private LavoriEdiliService lavoriEdiliService;
    
    @Autowired
    private LavoriElettriciServiceImpl lavoriElettriciService;
    
    
    @Autowired    
    private LavoriManutenzioneServiceImpl lavoriManutenzioneService;
    @Autowired 
    private LavoriManutenzioneServiceImpl lavoriManutenzioneServiceImpl;
    
    @Autowired    
    private LavoriTecnologiciService lavoriTecnologiciService;
    
    @Autowired    
    private LavoriRestauroService lavoriRestauroService;
    
    @Autowired
    private PreventivoServiceImpl preventivoService;
    
    @GetMapping("/selezionaUtente")
    public String selezionaUtente(Model model) {
        model.addAttribute("utenti", utenteService.findAll());
        model.addAttribute("tipiLavoro", Arrays.asList(LavoroEdile.values()));
        
        return "seleziona_utente";
    }

    @GetMapping("/crea")
    public String creaPreventivo(
            @RequestParam(required = true) Long utenteId,
            @RequestParam(required = true) String tipoLavoro, 
            Model model) {
        // Verifica che l'utenteId non sia nullo e che il tipoLavoro sia fornito'
        // Log per il debug dei parametri ricevuti
    	somma=0;
    	lavoriSelezionatiList.clear();
        System.out.println("Il clienteID è=" + utenteId);
        System.out.println("Il tipo di lavoro è=" + tipoLavoro);
        System.out.println("Esco dal phat `/preventivo/crea`  di tipo get ");
        // Verifica che l'utenteId non sia nullo e che il tipoLavoro sia fornito
        if (utenteId == null || tipoLavoro == null || tipoLavoro.isEmpty()) {
            model.addAttribute("errore", "Seleziona sia un utente che un tipo di lavorazione.");
            
         // Reinizializza la lista ogni volta
           // lavoriSelezionatiList = new ArrayList<>();
            //lavoriSelezionatiList.clear();
            // Ritorna alla vista di selezione con un messaggio di errore
            return "seleziona_utente"; // Modifica con il nome della tua vista di selezione se necessario
        }

        // Recupera l'utente dal repository e aggiungilo al modello per usarlo nella vista .
        utenteService.findById(utenteId).ifPresentOrElse(
            utente -> model.addAttribute("utente", utente),
            () -> model.addAttribute("errore", "Utente non trovato.")
        );        

        // Aggiungi il tipo di lavoro al modello per usarlo nella vista
        model.addAttribute("tipoLavoro", tipoLavoro);
        
        // Seleziona la lista giusta di lavori in base al tipoLavoro e la aggiungo nella vista.
        switch (tipoLavoro.toLowerCase()) {
            case "lavori_edili":
                model.addAttribute("lavori", lavoriEdiliService.findAll());
                break;
            case "lavori_elettrici":
                model.addAttribute("lavori", lavoriElettriciService.findAll());
                break;
            case "lavori_tecnologici":
                model.addAttribute("lavori", lavoriTecnologiciService.findAll());
                break;
            case "lavori_di_restauro":
	           	model.addAttribute("lavori", lavoriRestauroService.findAll());
	            break;
            case "lavori_manutenzione":
               	model.addAttribute("lavori", lavoriManutenzioneService.findAll());
                break;
            
            default:
                model.addAttribute("errore", "Tipo di lavoro non valido.");
                // Ritorna alla vista di selezione con un messaggio di errore
                return "seleziona_utente"; // Modifica con il nome della tua vista di selezione se necessario
        }
               
                
        return "crea_preventivo";// Ritorna alla vista crea_preventivo.html per creare il preventivo
    }


    /**
     * Gestisce la richiesta POST per salvare un preventivo.
     *
     * @param utenteId      ID dell'utente per il quale viene creato il preventivo.
     * @param tipoLavoro    Tipo di lavoro selezionato dall'utente (es. "edili", "elettrici").
     * @param idLavorazioni Lista degli ID delle lavorazioni selezionate dall'utente (può essere null o vuota se nessuna lavorazione è selezionata).
     * @param model         Oggetto Model per passare dati alla vista.
     * @return Nome della vista da visualizzare ("visualizza_preventivo" o "seleziona_utente" in caso di errore).
     */
    @PostMapping("/crea")
    public String salvaPreventivo(
            @RequestParam Long utenteId,
            @RequestParam String tipoLavoro,
            @RequestParam(name="idLavorazioni", required = false) List<Long> idLavorazioni,
            @RequestParam(name="listaLavori", required = false) List<Long> listaLavori,
            @RequestParam Map<String, String> quantita, // Mappa per ottenere le quantità delle lavorazioni
            Model model) {
    	
    	// Svuota la lista all'inizio
    	//lavoriSelezionatiList.clear(); 
    	
      
      //model.addAttribute("tipoLavoro", "");
      model.addAttribute("prezzoTotalePreventivo", 0);
      model.addAttribute("lavoriSelezionatiList",lavoriSelezionatiList);  
        /**
         * Metodo per filtrare e recuperare le quantità selezionate da una mappa di parametri
         * provenienti dal form HTML. La funzione cerca chiavi che iniziano con "quantita["
         * e associa le quantità corrispondenti agli ID delle lavorazioni selezionate.
         *
         * @param quantita Una mappa di parametri contenente chiavi nel formato "quantita[<idLavorazione>]"
         *                 e i relativi valori delle quantità.
         * @param idLavorazioni Una lista di ID delle lavorazioni selezionate dall'utente.
         * @return Una mappa contenente le quantità selezionate, con l'ID della lavorazione come chiave
         *         e la quantità associata come valore.
         */
        // Filtra solo le quantità selezionate
        // Ritorna una mappa con le quantità selezionate, con l'ID della lavorazione come chiave
        // e la quantità associata come valore.
        // Mappa per memorizzare le quantità selezionate
        Map<Long, Integer> quantitaSelezionate = new HashMap<>();        
        // Itera attraverso tutte le chiavi della mappa dei parametri se key inizia con "quantita["
        
        for (String key : quantita.keySet()) {
        	// Verifica se la chiave inizia con "quantita[" e se l'ID della lavorazione selezionata è presente'"]"
            if (key.startsWith("quantita[")) {
            	// Estrae l'ID della lavorazione dalla chiave usando il parsing del numero
                Long idLavorazione = Long.parseLong(key.replace("quantita[", "").replace("]", ""));
                
                if (idLavorazioni.contains(idLavorazione)) {
                    // Recupera la quantità dalla mappa quantita usando la chiave corrente
                    Integer qty = Integer.parseInt(quantita.get(key));
                    
                    // Aggiunge la quantità alla mappa quantitaSelezionate, associandola all'ID della lavorazione'
                    // Aggiunge l'ID della lavorazione e la quantità alla mappa delle quantità selezionate
                    quantitaSelezionate.put(idLavorazione, qty);
                    
//                    // Debug: stampa l'id della lavorazione e la quantità associata
//                    System.out.println("UtenteId: " + utenteId);
//                    System.out.println("TipoLavoro"+tipoLavoro);
//                    System.out.println("IdLavorazione: " + idLavorazione + ", Quantità: " + qty);
//                    

//                    model.addAttribute("tipoLavoro", "");
//                    model.addAttribute("lavoriSelezionatiList", new ArrayList<>());
//                    model.addAttribute("prezzoTotalePreventivo", 0.0);
                    
                 // Svuota la lista all'inizio
                 //	lavoriSelezionatiList.clear(); 
                	
                    switch (tipoLavoro.toLowerCase()) {
                    
                        case "lavori_edili":
                            LavoriEdili lavoridb1= lavoriEdiliService.findById(idLavorazione);                          
                            LavoriSelezionati lavoroSelezionato1 = new LavoriSelezionati(); // Crea una nuova istanza                             
                            lavoroSelezionato1.setClienteId(utenteId);
                            lavoroSelezionato1.setTipoLavoro(tipoLavoro);
                            lavoroSelezionato1.setQuantitaSelezionate(qty);
                            lavoroSelezionato1.setPrezzoUnitario(lavoridb1.getPrezzo());//da recuperare dal db .
                            lavoroSelezionato1.setDescrizione(lavoridb1.getDescrizione());//da recuperare dal db .
                            
                         // Calcola il prezzo totale per l'elemento corrente
                            double prezzoTotale1 = lavoroSelezionato1.getPrezzoUnitario() * qty;
                            lavoroSelezionato1.setPrezzoTotale(prezzoTotale1);
                            
                         // Aggiungi l'elemento alla lista
                            lavoriSelezionatiList.add(lavoroSelezionato1);
                            somma += lavoroSelezionato1.getPrezzoTotale();                         
                            System.out.println("somma : " + somma);                                                       
                            int nLavorazioni1=lavoriSelezionatiList.size();
                            model.addAttribute("utenteId", utenteId);
                            model.addAttribute("nLavorazioni", nLavorazioni1);
                            model.addAttribute("tipoLavoro", quantitaSelezionate);
                            model.addAttribute("prezzoTotalePreventivo", somma);
                            model.addAttribute("lavoriSelezionatiList", lavoriSelezionatiList);                            
                            break;
                        case "lavori_elettrici":
                            LavoriElettrici lavoridb2 = lavoriElettriciService.findById(idLavorazione);
                            LavoriSelezionati lavoroSelezionato2 = new LavoriSelezionati(); // Crea una nuova istanza                            
                            lavoroSelezionato2.setClienteId(utenteId);
                            lavoroSelezionato2.setTipoLavoro(tipoLavoro);
                            lavoroSelezionato2.setQuantitaSelezionate(qty);
                            lavoroSelezionato2.setPrezzoUnitario(lavoridb2.getPrezzo());//da recuperare dal db .
                            lavoroSelezionato2.setDescrizione(lavoridb2.getDescrizione());//da recuperare dal db .
                            
                         // Calcola il prezzo totale per l'elemento corrente
                            double prezzoTotale2 = lavoroSelezionato2.getPrezzoUnitario() * qty;
                            lavoroSelezionato2.setPrezzoTotale(prezzoTotale2);
                            
                         // Aggiungi l'elemento alla lista
                            lavoriSelezionatiList.add(lavoroSelezionato2);
                            somma += lavoroSelezionato2.getPrezzoTotale();
                            System.out.println("lavoroSelezionato2: " + lavoroSelezionato2 );  
                            System.out.println("somma : " + somma);   
                            int nLavorazioni2=lavoriSelezionatiList.size();
                            model.addAttribute("utenteId", utenteId);
                            model.addAttribute("nLavorazioni", nLavorazioni2);
                            model.addAttribute("tipoLavoro", quantitaSelezionate);
                            model.addAttribute("prezzoTotalePreventivo", somma);
                            model.addAttribute("lavoriSelezionatiList", lavoriSelezionatiList);                           	
                            break;                            
                        case "lavori_tecnologici":
                            LavoriTecnologici lavoridb3= lavoriTecnologiciService.findById(idLavorazione);
                            LavoriSelezionati lavoroSelezionato3 = new LavoriSelezionati(); // Crea una nuova istanza
                            lavoroSelezionato3.setClienteId(utenteId);
                            lavoroSelezionato3.setTipoLavoro(tipoLavoro);
                            lavoroSelezionato3.setPrezzoUnitario(lavoridb3.getPrezzo());//da recuperare dal db .
                            lavoroSelezionato3.setQuantitaSelezionate(qty); 
                            lavoroSelezionato3.setDescrizione(lavoridb3.getDescrizione());//da recuperare dal db . 
                            
                         // Calcola il prezzo totale per l'elemento corrente
                            double prezzoTotale3 = lavoroSelezionato3.getPrezzoUnitario() * qty;
                            lavoroSelezionato3.setPrezzoTotale(prezzoTotale3);
                            
                            // Aggiungi l'elemento alla lista
                            lavoriSelezionatiList.add(lavoroSelezionato3);
                            somma += lavoroSelezionato3.getPrezzoTotale();
                            System.out.println("lavoroSelezionato2: " + lavoroSelezionato3 );  
                            System.out.println("somma : " + somma);   
                            int nLavorazioni3=lavoriSelezionatiList.size();
                            model.addAttribute("utenteId", utenteId);
                            model.addAttribute("nLavorazioni", nLavorazioni3);
                            model.addAttribute("tipoLavoro", quantitaSelezionate);
                            model.addAttribute("prezzoTotalePreventivo", somma);
                            model.addAttribute("lavoriSelezionatiList", lavoriSelezionatiList);                            
                            break;                            
                        case "lavori_di_restauro":
            	           //	model.addAttribute("lavori", lavoriRestauroService.findAll());
            	           	LavoriRestauro lavoridb4= lavoriRestauroService.findLavoroRestauroById(idLavorazione);
            	           	LavoriSelezionati lavoroSelezionato4 = new LavoriSelezionati(); // Crea una nuova istanza
            	           	lavoroSelezionato4.setClienteId(utenteId);
                            lavoroSelezionato4.setTipoLavoro(tipoLavoro);
                            lavoroSelezionato4.setQuantitaSelezionate(qty);
                            lavoroSelezionato4.setPrezzoUnitario(lavoridb4.getPrezzo());//da recuperare dal db .
                            lavoroSelezionato4.setDescrizione(lavoridb4.getDescrizione());//da recuperare dal db .
                            
                         // Calcola il prezzo totale per l'elemento corrente
                            double prezzoTotale4 = lavoroSelezionato4.getPrezzoUnitario() * qty;
                            lavoroSelezionato4.setPrezzoTotale(prezzoTotale4);  
                            
                         // Aggiungi l'elemento alla lista
                            lavoriSelezionatiList.add(lavoroSelezionato4);
                            somma += lavoroSelezionato4.getPrezzoTotale();
                            System.out.println("lavoroSelezionato4: " + lavoroSelezionato4);  
                            System.out.println("somma : " + somma);   
                            int nLavorazioni4=lavoriSelezionatiList.size();
                            model.addAttribute("utenteId", utenteId);
                            model.addAttribute("nLavorazioni", nLavorazioni4);
                            model.addAttribute("tipoLavoro", quantitaSelezionate);
                            model.addAttribute("prezzoTotalePreventivo", somma);
                            model.addAttribute("lavoriSelezionatiList", lavoriSelezionatiList);                             
            	            break;
                        case "lavori_manutenzione":
                           	//model.addAttribute("lavori", lavoriManutenzioneService.findAll());
                           	LavoriManutenzione lavoridb5= lavoriManutenzioneService.findById(idLavorazione);
                           	LavoriSelezionati lavoroSelezionato5 = new LavoriSelezionati(); // Crea una nuova istanza
                           	lavoroSelezionato5.setClienteId(utenteId);
                            lavoroSelezionato5.setTipoLavoro(tipoLavoro);
                            lavoroSelezionato5.setQuantitaSelezionate(qty);
                            lavoroSelezionato5.setPrezzoUnitario(lavoridb5.getPrezzo());//da recuperare dal db .
                            lavoroSelezionato5.setDescrizione(lavoridb5.getDescrizione());//da recuperare dal db .
                                                        
                         // Calcola il prezzo totale per l'elemento corrente
                            double prezzoTotale5 = lavoroSelezionato5.getPrezzoUnitario() * qty;
                            lavoroSelezionato5.setPrezzoTotale(prezzoTotale5); 
                            
                         // Aggiungi l'elemento alla lista
                            lavoriSelezionatiList.add(lavoroSelezionato5);
                            somma += lavoroSelezionato5.getPrezzoTotale();
                            System.out.println("lavoroSelezionato5: " + lavoroSelezionato5);  
                            System.out.println("somma : " + somma);   
                            int nLavorazioni5=lavoriSelezionatiList.size();
                            model.addAttribute("utenteId", utenteId);
                            model.addAttribute("nLavorazioni", nLavorazioni5);
                            model.addAttribute("tipoLavoro", quantitaSelezionate);
                            model.addAttribute("prezzoTotalePreventivo", somma);
                            model.addAttribute("lavoriSelezionatiList", lavoriSelezionatiList); 
                            break;
                            
                        
                        default:
                            model.addAttribute("errore", "Tipo di lavoro non valido.");
                            // Ritorna alla vista di selezione con un messaggio di errore
                            return "seleziona_utente"; // Modifica con il nome della tua vista di selezione se necessario
                    }

                }
            } 
        }

     // Aggiungi la mappa quantitaSelezionate al model
        model.addAttribute("quantitaSelezionate", quantitaSelezionate);
        // Stampa le quantità selezionate per il debug funziona ma lo commentato
//        quantitaSelezionate.forEach((id, qty) -> System.out.println("IdLavorazione: " + id + ", Quantità: " + qty));

        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
               
        // Recupera l'utente dal repository e aggiungilo al modello, gestendo l'errore se l'utente non viene trovato
        utenteService.findById(utenteId).ifPresentOrElse(
            utente -> model.addAttribute("utente", utente),
            () -> model.addAttribute("errore", "Utente non trovato.")
        );

        // Se l'utente non è presente (per esempio, se è stato aggiunto un errore), ritorna alla vista di selezione dell'utente
        if (model.containsAttribute("errore")) {
            return "seleziona_utente"; // Torna alla vista di selezione utente se l'utente non esiste
        }


        // Aggiungi gli attributi necessari al modello
        model.addAttribute("idLavorazioni", idLavorazioni);
        model.addAttribute("tipoLavoro", tipoLavoro);
        
        
     // Seleziona la lista giusta di lavori in base al tipoLavoro e ritorno la lista dei lavori selezionati.
        switch (tipoLavoro.toLowerCase()) {
            case "lavori_edili":
               model.addAttribute("lavori", lavoriEdiliService.findLavoriByIds(idLavorazioni));
                break;
            case "lavori_elettrici":
                model.addAttribute("lavori", lavoriElettriciService.findLavoriByIds(idLavorazioni));
                break;
            case "lavori_tecnologici":
                model.addAttribute("lavori", lavoriTecnologiciService.findLavoriByIds(idLavorazioni));
                System.out.println("lavori_tecnologici"+(lavoriTecnologiciService.findLavoriByIds(idLavorazioni)));
                break;
            case "lavori_di_restauro":
	           	model.addAttribute("lavori", lavoriRestauroService.findLavoriByIds(idLavorazioni));
	           	System.out.println("lavori"+(lavoriRestauroService.findLavoriByIds(idLavorazioni)));
	            break;
            case "lavori_manutenzione":
               	model.addAttribute("lavori", lavoriManutenzioneService.findLavoriByIds(idLavorazioni));
               	System.out.println("lavori_manutenzione"+(lavoriManutenzioneService.findLavoriByIds(idLavorazioni)));
                break;
            
            default:
                model.addAttribute("errore", "Tipo di lavoro non valido.");
                
                // Ritorna alla vista di selezione con un messaggio di errore
                return "seleziona_utente"; // Modifica con il nome della tua vista di selezione se necessario
        }
       
        // Ritorna alla vista di visualizzazione del preventivo
        System.out.println("sto andando a visualizza preventivo");
       
        return "visualizza_preventivo";
    }

    @PostMapping("/accetta")
    public String accettaPreventivo(
    		//@RequestParam Long idUtente,
    		@RequestParam String utenteNome,
    		@RequestParam String tipoLavoro,
    		@RequestParam List<String> idLavorazioni,     		
    		@RequestParam Map<String, String> quantitaSelezionate,
    		@RequestParam List<String> lavoriSelezionatiList,
    		
       		Model model) {
    	System.out.println(lavoriSelezionatiList);
    	//model.addAttribute("idUtente", idUtente);
    	model.addAttribute("utenteNome", utenteNome);
    	model.addAttribute("tipoLavoro", tipoLavoro);
    	model.addAttribute("idLavorazioni", idLavorazioni);
    	model.addAttribute("quantitaSelezionate", quantitaSelezionate);
    	model.addAttribute("lavoriSelezionatiList", lavoriSelezionatiList);
    	

        return "preventivo_accettato";
    }
    
}
