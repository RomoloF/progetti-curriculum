package it.preventivo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.preventivo.entity.LavoriElettrici;
import it.preventivo.entity.LavoriManutenzione;
import it.preventivo.entity.LavoriRestauro;
import it.preventivo.repository.LavoriManutenzioneRepository;

@Service
public class LavoriManutenzioneServiceImpl   {

    @Autowired
    private LavoriManutenzioneRepository lavoriManutenzioneRepository;

    /**
     * Restituisce tutti i Lavori di Manutenzione.
     * @return lista di LavoriManutenzione
     */
    public List<LavoriManutenzione> findAll() {
        return lavoriManutenzioneRepository.findAll();
    }
    
    /**
     * Trova un Lavoro di Manutenzione per ID di tipo Long.
     * @param id
     * @return Optional di LavoriManutenzione
     */
    public LavoriManutenzione findById(long id) {
        Optional<LavoriManutenzione> result = lavoriManutenzioneRepository.findById(id);
        return result.orElse(null);
    }
    
    /**
     * Salva o aggiorna un Lavoro di Manutenzione.
     * @param lavoriManutenzione
     * @return LavoriManutenzione salvato
     */
    public LavoriManutenzione save(LavoriManutenzione lavoriManutenzione) {
        return lavoriManutenzioneRepository.save(lavoriManutenzione);
    }
    
    /**
     * Cancella un Lavoro di Manutenzione per ID di tipo Long.
     * @param id
     */
   public void deleteById(Long id) {
		lavoriManutenzioneRepository.deleteById(id);
		
	}

	// Metodo per trovare lavori per un insieme di ID
    public List<LavoriManutenzione> findLavoriByIds(List<Long> idLavorazioni) {
        return lavoriManutenzioneRepository.findAllById(idLavorazioni);
    }
	
    public List<LavoriManutenzione> findByDescrizioneContaining(String descrizione) {
		return lavoriManutenzioneRepository.findByDescrizioneContaining(descrizione);
	}
}






