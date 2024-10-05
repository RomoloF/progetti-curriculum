package it.preventivo.entity.preventivo_definito.controller;

import it.preventivo.entity.preventivo_definito.PreventivoDefinito;
import it.preventivo.entity.preventivo_definito.service.PreventivoDefinitoService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller per gestire le operazioni sui preventivi.
 */
@Controller
@RequestMapping("/preventivoDefinito")
public class PreventivoDefinitoController {

    @Autowired
    private PreventivoDefinitoService preventivoService;

    /**
     * Metodo per gestire la richiesta di accettazione del preventivo.
     *
     * @param utenteNome            Nome dell'utente.
     * @param tipoLavoro            Tipo di lavoro selezionato.
     * @param idLavorazioni         ID delle lavorazioni.
     * @param lavoriSelezionatiList Lista dei lavori selezionati.
     * @param model                 Il modello per passare i dati alla vista.
     * @return Il nome della vista per confermare l'accettazione del preventivo.
     */
    @PostMapping("/accetta")
    public String accettaPreventivo(
    		@RequestParam("idUtente") Long idUtente,
            @RequestParam("utenteNome") String utenteNome,
            @RequestParam("tipoLavoro") String tipoLavoro,
            @RequestParam("prezzoTotalePreventivo") double prezzoTotalePreventivo,
           // @RequestParam("idLavorazioni")List<Long> idLavorazioni, // Cambia il tipo a List<Long>
            @RequestParam("lavoriSelezionatiList") String lavoriSelezionatiList,
            Model model) {

        // Creazione di un nuovo oggetto PreventivoDefinito
        PreventivoDefinito preventivo = new PreventivoDefinito();
        preventivo.setClienteId(idUtente);
        preventivo.setNomePreventivo("Preventivo per " + utenteNome);
        preventivo.setTipoLavoro(tipoLavoro);
        // Imposta altri campi necessari come clienteId o prezzo totale
        preventivo.setDataCreazione(new java.util.Date());
        
        preventivo.setPrezzoTotalePreventivo(prezzoTotalePreventivo);
        System.out.println("Stampo il prezzo totale preventivo = € "+prezzoTotalePreventivo);
        System.out.println("Descrizione = "+lavoriSelezionatiList);
        
   //================================================================================================     
        
        
//     // Parsing manuale della stringa
        List<String> descrizioni = new ArrayList<>();
        List<String> listadescrizioni = new ArrayList<String>();
        // Separazione degli Oggetti: La stringa è suddivisa utilizzando "LavoriSelezionati", separando così ogni oggetto di lavoro.
        String[] lavoriArray = lavoriSelezionatiList.split("LavoriSelezionati");
        int numeroDescrizione = 1; // Inizializza un contatore per la numerazione
        //Estrazione della Descrizione: Per ogni elemento, cerchiamo il campo descrizione= e lo estraiamo fino alla prossima virgola.
        for (String lavoro : lavoriArray) {
            if (lavoro.contains("descrizione=")) {
            	
            	// Pulizia della Descrizione: Utilizziamo replace("\"", "") per rimuovere eventuali virgolette residue.
            	// Risultato del split: La funzione split("descrizione=") divide la stringa in due parti:
            	// Prima parte ([0]): " [id=0, "
            	// Seconda parte ([1]): "diametro nominale 3/8, spess. 2 mm, prezzoUnitario=10.7, quantitaSelezionate=2, prezzoTotale=21.4, clienteId=14, tipoLavoro=LAVORI_TECNOLOGICI]"
                // Indice dell'Array: Seleziona la seconda parte dell'array risultante dal metodo split. In questo caso, otteniamo:
            	// "diametro nominale 3/8, spess. 2 mm, prezzoUnitario=10.7, quantitaSelezionate=2, prezzoTotale=21.4, clienteId=14, tipoLavoro=LAVORI_TECNOLOGICI]"
            	// .split(",")[0]:

            	//	Funzione split(","): Divide nuovamente la stringa selezionata ("diametro nominale 3/8, spess. 2 mm, prezzoUnitario=10.7, ...") in un array di sottostringhe ogni volta che trova una virgola ,.
            	//	Risultato del split(","):
            	//	Prima parte ([0]): "diametro nominale 3/8"
            	//	Altre parti ([1], [2], ecc.): " spess. 2 mm", " prezzoUnitario=10.7", ecc.
            	//	Selezione della Prima Parte ([0]): Scegliamo la prima parte dell'array risultante, ovvero:
            	// "diametro nominale 3/8"
            	String descrizione = lavoro.split("descrizione=")[1].split(",")[0].replace("\"", "");
          //  	List<String> listadescrizioni = new ArrayList<String>();
            	listadescrizioni.add(descrizione);
            	
                descrizioni.add("Numero della descrizione "+numeroDescrizione + ": " + descrizione.trim());
                numeroDescrizione++; // Incrementa il contatore
            }
        }

        // Concatenazione delle descrizioni numerate con "andare a capo"
        String descrizioneConcatenata = String.join("\n", descrizioni);
        
        // Imposta la descrizione concatenata nell'oggetto PreventivoDefinito
        preventivo.setDescrizione(descrizioneConcatenata);
        System.out.println("Stampo descrizione concatenata >>>> \n" + descrizioneConcatenata + "...");
        model.addAttribute("descrizioneConcatenata", descrizioneConcatenata);
        model.addAttribute("listadescrizioni", listadescrizioni);
  //================================================================      
        
        
        // Salva il preventivo utilizzando il servizio
        PreventivoDefinito preventivoSalvato = preventivoService.salvaPreventivo(preventivo);

        // Aggiungi il preventivo al modello per visualizzarlo nella vista
        model.addAttribute("preventivo", preventivoSalvato);
        model.addAttribute("message", "Preventivo accettato con successo e salvato nel DB !");

        // Reindirizza alla vista di conferma
        return "preventivoDefinito/confermaPreventivo"; // Nome della vista per la conferma
    }
    /**
     * Metodo per visualizzare i dettagli del preventivo di un utente selezionato.
     * @param idUtente ID dell'utente selezionato.
     * @param model Oggetto Model per passare dati alla vista.
     * @return Nome della vista Thymeleaf per visualizzare i dettagli del preventivo.
     */
    @GetMapping("/visualizza")
    public String visualizzaPreventivoPerUtente(@RequestParam("idUtente") Long idUtente, Model model) {
        // Recupera i preventivi dell'utente dal servizio
        List<PreventivoDefinito> preventiviUtente = preventivoService.trovaPreventiviPerUtente(idUtente);

        // Aggiungi i preventivi al modello
        model.addAttribute("preventiviUtente", preventiviUtente);
        return "preventivoDefinito/visualizzaPreventivi"; // Nome della vista Thymeleaf
    }
}