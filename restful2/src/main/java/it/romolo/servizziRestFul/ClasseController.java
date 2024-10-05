package it.romolo.servizziRestFul;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.romolo.entity.Classe;
import it.romolo.repository.ClasseRepository;

@RestController
@RequestMapping("/api/classi")
public class ClasseController {

	@Autowired
	private ClasseRepository classeRepository;

	private Logger logger = LoggerFactory.getLogger(ClasseController.class);

	@GetMapping
	public List<Classe> getAllClassi() {
		System.out.println("CIAO ROMOLO");
		System.out.println("CIAO ROMOLO TI STO RESTITUENDO UNA LISTA DI CLASSI");
		return classeRepository.findAll();
	}

	/**
	 * Questo metodo gestisce le richieste GET HTTP all'endpoint "/{id}".
	 * L'annotazione @GetMapping indica che questo metodo mappa a una richiesta GET.
	 * Il valore "{id}" nel path indica che l'endpoint accetta un parametro di path
	 * variabile chiamato "id".
	 *
	 * @param id L'ID della Classe da recuperare. Questo parametro deve essere un
	 *           intero (Integer).
	 * @return Un oggetto ResponseEntity contenente la Classe trovata o un messaggio
	 *         di "not found" se la Classe non viene trovata.
	 * @throws Exception Qualsiasi eccezione che potrebbe verificarsi durante il
	 *                   recupero della Classe.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Classe> getClasseById(@PathVariable Integer id) {
		/**
		 * Questo codice tenta di recuperare la Classe dal repository utilizzando il suo
		 * ID. Il metodo findById del repository restituisce un Optional contenente la
		 * Classe se trovata, altrimenti un Optional vuoto.
		 */
		Optional<Classe> classe = classeRepository.findById(id);
		/**
		 * Questo codice controlla se l'Optional contiene una Classe. Se la Classe è
		 * presente, viene utilizzata la funzione statica "ok" della classe
		 * ResponseEntity per creare un oggetto ResponseEntity con lo stato HTTP 200
		 * (OK) e la Classe trovata come corpo della risposta.
		 */
		return classe.map(ResponseEntity::ok)
				/**
				 * Se l'Optional è vuoto, significa che la Classe non è stata trovata. In questo
				 * caso, viene utilizzata la funzione "notFound" della classe ResponseEntity per
				 * creare un oggetto ResponseEntity con lo stato HTTP 404 (Not Found) e un corpo
				 * di risposta vuoto.
				 */
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public Classe createClasse(@RequestBody Classe classe) {
		return classeRepository.save(classe);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Classe> updateClasse(@PathVariable Integer id, @RequestBody Classe classeDetails) {
		Optional<Classe> optionalClasse = classeRepository.findById(id);
		if (optionalClasse.isPresent()) {
			Classe classe = optionalClasse.get();

			classe.setId(classeDetails.getId());
			classe.setAnnoScolastico(classeDetails.getAnnoScolastico());
			classe.setClasseAnno(classeDetails.getClasseAnno());
			classe.setSezione(classeDetails.getSezione());
			classe.setPiano(classeDetails.getPiano());
			classe.setAulaNum(classeDetails.getAulaNum());
			classe.setStudentes(classeDetails.getStudentes());

			return ResponseEntity.ok(classeRepository.save(classe));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteClasse(@PathVariable Integer id) {
		Optional<Classe> classe = classeRepository.findById(id);
		if (classe.isPresent()) {
			classeRepository.delete(classe.get());
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Questi metodi utilizzano le query definite nell'interfaccia ClasseRepository
	 * per recuperare liste di Classi filtrate in base a diversi criteri. Ad
	 * esempio, il metodo getClasseByAnnoScolastico utilizza la query
	 * classeRepository.getClasseByAnnoScolastico(annoScolastico) per recuperare le
	 * Classi associate all'anno scolastico specificato.
	 *
	 * In questo modo, il controller offre funzionalità complete per la gestione
	 * delle Classi, consentendo di recuperare informazioni sia singolarmente per ID
	 * che in maniera filtrata in base a diversi criteri.
	 */
	/**
	 * Questo metodo recupera una lista di Classi filtrate per anno scolastico.
	 *
	 * @param annoScolastico L'anno scolastico da utilizzare per il filtro.
	 * @return Una lista di Classi che corrispondono all'anno scolastico
	 *         specificato.
	 */
	@GetMapping("/annoScolastico/{annoScolastico}")
	public List<Classe> getClasseByAnnoScolastico(@PathVariable String annoScolastico) {
		return classeRepository.getClasseByAnnoScolastico(annoScolastico);
	}

	/**
	 * Questo metodo recupera una lista di Classi filtrate per anno e classe (es.
	 * 5A).
	 *
	 * @param classeAnno L'anno e la classe da utilizzare per il filtro (es. 5A).
	 * @return Una lista di Classi che corrispondono all'anno e alla classe
	 *         specificati.
	 */
	@GetMapping("/classeAnno/{classeAnno}")
	public List<Classe> getClasseAnno(@PathVariable String classeAnno) {
		return classeRepository.getClasseByAnnoScolastico(classeAnno);
	}

	/**
	 * Questo metodo recupera una lista di Classi filtrate per piano (es. primo
	 * piano).
	 *
	 * @param piano Il piano da utilizzare per il filtro.
	 * @return Una lista di Classi che corrispondono al piano specificato.
	 */
	@GetMapping("/piano/{piano}")
	public List<Classe> getClasseByPiano(@PathVariable String piano) {
		return classeRepository.getClasseByPiano(piano);
	}

	/**
	 * Questo metodo recupera una lista di Classi filtrate per sezione (es. A).
	 *
	 * @param sezione La sezione da utilizzare per il filtro.
	 * @return Una lista di Classi che corrispondono alla sezione specificata.
	 */
	@GetMapping("/sezione/{sezione}")
	public List<Classe> getClasseBySezione(@PathVariable String sezione) {
		return classeRepository.getClasseBySezione(sezione);
	}

}
