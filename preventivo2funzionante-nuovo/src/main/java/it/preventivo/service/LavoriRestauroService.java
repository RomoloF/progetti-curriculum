package it.preventivo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.preventivo.entity.LavoriRestauro;
import it.preventivo.repository.LavoriRestauroRepository;

@Service
public class LavoriRestauroService {

    @Autowired
    private LavoriRestauroRepository lavoriRestauroRepository;

    public List<LavoriRestauro> findAll() {
        return lavoriRestauroRepository.findAll();
    }

    public LavoriRestauro findById(Long id) {
    	
        return lavoriRestauroRepository.getById(id);
    }

    public LavoriRestauro save(LavoriRestauro lavoriRestauro) {
        return lavoriRestauroRepository.save(lavoriRestauro);
    }

    public void deleteById(Long id) {
        lavoriRestauroRepository.deleteById(id);
    }

    // Metodo per trovare lavori per un insieme di ID
    public List<LavoriRestauro> findLavoriByIds(List<Long> idLavorazioni) {
        return lavoriRestauroRepository.findAllById(idLavorazioni);
    }

    /**
     * Trova un lavoro di restauro per ID.
     *
     * @param id L'ID del lavoro di restauro.
     * @return Lavoro di restauro trovato o null se non esiste.
     */
    
    public LavoriRestauro findLavoroRestauroById(Long id) {
        return lavoriRestauroRepository.findLavoroRestauroById(id);
    }
	
	
}
