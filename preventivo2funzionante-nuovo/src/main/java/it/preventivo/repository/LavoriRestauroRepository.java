package it.preventivo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.micrometer.common.lang.Nullable;
import it.preventivo.entity.LavoriRestauro;

public interface LavoriRestauroRepository extends JpaRepository<LavoriRestauro, Long> {
	
	// Puoi aggiungere eventuali query personalizzate qui, se necessario
	// Ad esempio:
	 /**
     * Trova i lavori di restauro basati su una lista di ID.
     * 
     * @param ids Lista degli ID dei lavori da cercare.
     * @return Lista di lavori di restauro corrispondenti agli ID forniti.
     */
    List<LavoriRestauro> findByIdIn(List<Long> ids);
    
    
 // Metodo personalizzato che restituisce un LavoriRestauro in base all'ID.
    /**
     * Trova un lavoro di restauro in base all'ID fornito.
     * 
     * @param id L'ID del lavoro di restauro da cercare.
     * @return Lavoro di restauro corrispondente all'ID fornito o null se non trovato.
     */
    @Query("SELECT lr FROM LavoriRestauro lr WHERE lr.id = :id")
    @Nullable
    LavoriRestauro findLavoroRestauroById(@Param("id") Long id);
    
    
    
}
