package it.romolo.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import it.romolo.model.Classe;

@Service
public class ClasseService {

	@Autowired
	private RestTemplate restTemplate;

	private final String BASE_URL = "http://localhost:8086/api/classi"; // Sostituisci con l'URL reale dell'API

	/**
	 * Cerca Classe per id
	 *
	 * @param id
	 * @return Classe
	 */
	public Classe getClasseById(int id) {
		String url = BASE_URL + "/" + id;
		return restTemplate.getForObject(url, Classe.class);
	}

	/**
	 * Crea una nuova Classe
	 *
	 * @param classe
	 * @return
	 */
	public Classe createClasse(Classe classe) {
		String url = BASE_URL;
		return restTemplate.postForObject(url, classe, Classe.class);
	}

	/**
	 * Ritorna una lista di Classi
	 *
	 * @return
	 */
	public List<Classe> getAllClassi() {
		String url = BASE_URL;
		Classe[] classiArray = restTemplate.getForObject(url, Classe[].class);
		return Arrays.asList(classiArray);
	}

	/**
	 * Modifica una Classe esistente
	 *
	 * @param classe
	 * @return
	 */
	public Classe updateClasse(Classe classe) {
		String url = BASE_URL + "/" + classe.getId();
		restTemplate.put(url, classe);
		return classe; // Ritorna l'oggetto aggiornato (opzionale)
	}

	/**
	 * Elimina una Classe.
	 *
	 * @param id l'ID della classe da eliminare
	 */
	public void deleteClasse(int id) {
		String url = BASE_URL + "/" + id;
		restTemplate.delete(url);
	}

	/**
	 * Cerca Classi per anno scolastico
	 *
	 * @param annoScolastico
	 * @return Lista di Classi
	 */
	public List<Classe> searchClassiByAnnoScolastico(String annoScolasticoString) {
			int annoScolastico=Integer.parseInt(annoScolasticoString.trim());
			System.out.println(annoScolastico);

		String url = UriComponentsBuilder.fromHttpUrl(BASE_URL).queryParam("annoScolastico", annoScolastico)
				.toUriString();
		Classe[] classiArray = restTemplate.getForObject(url, Classe[].class);
		return Arrays.asList(classiArray);
	}

	/**
	 * Cerca Classi per nome (ad esempio, sezione o qualsiasi altro campo che
	 * rappresenta il nome)
	 *
	 * @param nome
	 * @return Lista di Classi
	 */
	public List<Classe> searchClassiByNome(String nome) {
		String url = UriComponentsBuilder.fromHttpUrl(BASE_URL).queryParam("nome", nome).toUriString();
		Classe[] classiArray = restTemplate.getForObject(url, Classe[].class);
		return Arrays.asList(classiArray);
	}

	/**
	 * Cerca Classi per sezione
	 *
	 * @param sezione
	 * @return Lista di Classi
	 */
	public List<Classe> searchClassiBySezione(String sezione) {
		String url = UriComponentsBuilder.fromHttpUrl(BASE_URL).queryParam("sezione", sezione).toUriString();
		Classe[] classiArray = restTemplate.getForObject(url, Classe[].class);
		return Arrays.asList(classiArray);
	}

	/**
	 * Cerca Classi per numero dell'aula
	 *
	 * @param aulaNum
	 * @return Lista di Classi
	 */
	public List<Classe> searchClassiByAulaNum(String aulaNum) {
		String url = UriComponentsBuilder.fromHttpUrl(BASE_URL).queryParam("aulaNum", aulaNum).toUriString();
		Classe[] classiArray = restTemplate.getForObject(url, Classe[].class);
		return Arrays.asList(classiArray);
	}

	/**
	 * Cerca Classi per piano
	 *
	 * @param piano
	 * @return Lista di Classi
	 */
	public List<Classe> searchClassiByPiano(String piano) {
		String url = UriComponentsBuilder.fromHttpUrl(BASE_URL).queryParam("piano", piano).toUriString();
		Classe[] classiArray = restTemplate.getForObject(url, Classe[].class);
		return Arrays.asList(classiArray);
	}
}
