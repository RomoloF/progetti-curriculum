package it.preventivo.entity.preventivo_definito.repository;

import it.preventivo.entity.preventivo_definito.PreventivoDefinito;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository per l'entità PreventivoDefinito.
 */
@Repository
public interface PreventivoDefinitoRepository extends JpaRepository<PreventivoDefinito, Long> {
	
	 /**
     * Trova tutti i preventivi associati a un determinato cliente.
     * @param clienteId ID del cliente.
     * @return Lista di preventivi.
     */
	List<PreventivoDefinito> findByClienteId(Long idUtente);
    // È possibile aggiungere metodi di query personalizzati qui se necessario
}