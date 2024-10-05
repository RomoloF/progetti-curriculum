package it.romolo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.romolo.entity.Studente;

@Repository
public interface StudenteRepository extends JpaRepository<Studente, Integer> {


	List<Studente> getStudentiByCognome(String cognome);

	List<Studente> getStudentiByNome(String nome);

	List<Studente> getStudentiByEmail(String email);


	/**
	 * Metodo per ottenere tutti gli studenti di una classe
	 * Oppure tramite l'ID della classe
	 * @param classe
	 * @return
	 */
//	List<Studente> getStudentiByClasse(Classe classe);
//	List<Studente> getStudentiById_classe(Integer idClasse);
//
//	// Metodo per trovare tutti gli studenti di una specifica classe usando l'oggetto Classe
//    List<Studente> findByClasse(Classe classe);
//
////    // Metodo per trovare tutti gli studenti di una specifica classe usando l'ID della Classe
////    List<Studente> findByClasseId(Integer classeId);
////
//
//    // Metodo per trovare tutti gli studenti di una specifica classe usando l'ID della Classe con JPQL
//    @Query("SELECT s FROM Studente s WHERE s.classe.id = :classeId")
//    List<Studente> findByClasseId(@Param("classeId") Integer classeId);
}


