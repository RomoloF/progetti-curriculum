package it.romolo.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import it.romolo.model.Studente;

@Service
public class StudenteService {

	@Autowired
	private RestTemplate restTemplate;
	private Logger loger = LoggerFactory.getLogger(StudenteService.class);

	private final String BASE_URL = "http://localhost:8086/api/studenti"; // Sostituisci con l'URL reale del servizio
																			// API

	public Studente getStudenteById(int id) {
		String url = BASE_URL + "/" + id;
		System.out.println(url);
		Studente studente=restTemplate.getForObject(url, Studente.class);
		System.out.println("Lo studente ritornato è [ "+studente+" ] con id :"+id);
		return studente;
	}

	public Studente createStudente(Studente studente) {
		System.out.println(studente);
		return restTemplate.postForObject(BASE_URL, studente, Studente.class);
	}

	public List<Studente> getAllStudenti() {
		String url = BASE_URL;
		Studente[] studentiArray = restTemplate.getForObject(url, Studente[].class);
		return Arrays.asList(studentiArray);
	}

//    public Studente updateStudente( Studente studente) {
//        String url = BASE_URL + "/{id}" + studente;
//        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//        System.out.println("Sono entrato nel metodo updateStudente della classe StudenteService ");
//        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//        System.out.println("Lo studente è >>"+studente);
//        //Classe classe=c.getClass();
//    //    System.out.println("La classe che mi a restituito studente è >>>"+classe);
//        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//        System.out.println("L' api remota è >> "+url);
//        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//        restTemplate.put(url,studente);
//		return studente;
//    }

	/**
	 * Questo è il metodo updateStudente che prende due argomenti: un id che
	 * identifica lo studente da aggiornare e un oggetto studente che contiene i
	 * dati aggiornati dello studente.
	 */
	public void updateStudente(int id, Studente studente) {
		/**
		 * Qui viene costruita l'URL per la richiesta. BASE_URL è una variabile che
		 * contiene l'URL di base del server, e aggiorna è un endpoint che indica
		 * l'azione di aggiornamento, seguito dall'id dello studente.
		 */
		String url = BASE_URL + "/aggiorna/" + id;

		/**
		 * Vengono creati gli header per la richiesta HTTP. In questo caso, viene
		 * specificato che il corpo della richiesta sarà in formato JSON.
		 */
		HttpHeaders headers = new HttpHeaders();
		/**
		 *
		 */
		headers.setContentType(MediaType.APPLICATION_JSON);
		/**
		 * Viene creato un oggetto HttpEntity che rappresenta la richiesta HTTP.
		 * Contiene il corpo della richiesta (studente) e gli headers.
		 */
		HttpEntity<Studente> requestEntity = new HttpEntity<>(studente, headers);

		/**
		 * Viene effettuata la richiesta HTTP di tipo PUT al server. Viene utilizzato un
		 * oggetto RestTemplate per fare la richiesta. Il metodo exchange permette di
		 * specificare l'URL, il metodo HTTP (PUT in questo caso), l'entità della
		 * richiesta e il tipo di risposta atteso (Studente.class).
		 */
		ResponseEntity<Studente> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Studente.class);

		/**
		 * Viene controllato lo stato della risposta. Se la richiesta ha avuto successo
		 * (codice di stato HTTP 2xx), viene stampato un messaggio di conferma con i
		 * dettagli dello studente aggiornato. Altrimenti, viene stampato un messaggio
		 * di errore con il codice di stato HTTP ricevuto.
		 */
		if (response.getStatusCode().is2xxSuccessful()) {
			System.out.println("Studente aggiornato con successo: " + response.getBody());
		} else {
			System.out.println("Errore nell'aggiornamento dello studente: " + response.getStatusCode());
		}
	}

	public void deleteStudente(int id) {
		String url = BASE_URL + "/" + id;
		loger.info("L' URL che hai chiamato è >>>>", url);
		loger.info("Stai cancellando lo studente con id{}", id);
		restTemplate.delete(url);
		loger.info("Hai cancellato lo studente con id{}", id);
	}
///////////////////////////////////////////////////////////////////////////////////////////////
	// Ulteriori metodi di ricerca per lo Studente

	public List<Studente> cercaPerNome(String nome ) {
		System.out.println(" Sto dentro StudenteService sul metodo cercaPerNome il nome e':"+nome);

		// Codifica del parametro nome per evitare problemi con caratteri speciali
        String encodedNome = URLEncoder.encode(nome, StandardCharsets.UTF_8);


      //    Composizione dell'URL [curl -X 'GET' \'http://localhost:8085/api/studenti/cercaPernome/{nome}?nome=romolo' \
      //    -H 'accept: (*/*')


		String url = BASE_URL+"/cercaPerNome/"  ;
		System.out.println(" la base_url e' >>>"+url);

		 // Aggiunta del parametro "nome" all'URL usando UriComponentsBuilder
        String url1 = UriComponentsBuilder.fromUriString(url)
                .queryParam("nome", nome)
                .buildAndExpand(nome)
                .toUriString();



        System.out.println(" Sto dentro StudenteService URL è':>>>>>>>>"+url);
		System.out.println(" Sto dentro StudenteService URL1 è':>>>>>>>>"+url1);
		/**
		 * getForObject(url, classType) – recuperare una rappresentazione eseguendo un
		 * GET sull'URL. La risposta (se presente) viene unmarshalling per il tipo di
		 * classe specificato e restituita.
		 */
		Studente[] studentiArray = restTemplate.getForObject(BASE_URL, Studente[].class);
		return Arrays.asList(studentiArray);
	}/////Fine Metodo cercaPer Nome


///////////////////////////////////////////////////////////////////////////////////////////////

	public List<Studente> cercaPerCognome(String cognome) {
		String url = BASE_URL + "/cercaPerCognome/{cognome}" + cognome;
		Studente[] studentiArray = restTemplate.getForObject(url, Studente[].class);
		List<Studente> listNome=Arrays.asList(studentiArray);
		return listNome;
	}

	public List<Studente> cercaPerAnno(int anno) {
		String url = BASE_URL + "/cerca/anno/" + anno;
		Studente[] studentiArray = restTemplate.getForObject(url, Studente[].class);
		return Arrays.asList(studentiArray);
	}

	public List<Studente> cercaPerEmail(String email) {
		String url = BASE_URL + "/cercaPerEmail/{email}" + email;
		Studente[] studentiArray = restTemplate.getForObject(url, Studente[].class);
		return Arrays.asList(studentiArray);
	}

	public List<Studente> cercaPerClasse(int idClasse) {
		String url = BASE_URL + "/cerca/classe/" + idClasse;
		Studente[] studentiArray = restTemplate.getForObject(url, Studente[].class);
		return Arrays.asList(studentiArray);
	}
}
