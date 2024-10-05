package it.romolo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.romolo.entity.Classe;

@Repository
public interface ClasseRepository extends JpaRepository<Classe, Integer> {


	List<Classe> getClasseByAnnoScolastico(String annoScolastico);

	//List<Classe> getClasseAnno(String classeAnno);

	List<Classe> getClasseByPiano(String piano);

	List<Classe> getClasseBySezione(String sezione);

}
