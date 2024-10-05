package it.preventivo.repository;



import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.preventivo.entity.LavoriManutenzione;
/**
 * Repository per l'entità LavoriManutenzione.
 */
@Repository
public interface LavoriManutenzioneRepository extends JpaRepository<LavoriManutenzione, Long> {
    // Puoi aggiungere metodi di query personalizzati se necessario
	/**
     * Trova i lavori manutenzione basati su una lista di ID.
     * 
     * @param ids Lista degli ID dei lavori da cercare.
     * @return Lista di lavori manutenzione corrispondenti agli ID forniti.
     */
	List<LavoriManutenzione> findByIdIn(List<Long> ids);
	
	
	/**
     * Trova lavori di manutenzione la cui descrizione contiene la sottostringa specificata.
     * @param descrizione La sottostringa da cercare nella descrizione.
     * @return Lista di lavori di manutenzione che contengono la sottostringa nella descrizione.
     */
    List<LavoriManutenzione> findByDescrizioneContaining(String descrizione);
}
