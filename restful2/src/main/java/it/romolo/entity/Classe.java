package it.romolo.entity;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="classe")

public class Classe implements Serializable {
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name="anno_scolastico")
	private String annoScolastico;

	@Column(name="aula_num")
	private String aulaNum;

	@Column(name="classe_anno")
	private String classeAnno;

	private String piano;

	private String sezione;

	//Associazione biderizionale con lo  studente                                                                                                                                Studente
	@OneToMany(mappedBy="classe")

	  @JsonIgnore
	private List<Studente> studentes;

	// Costruttore di default
	public Classe() {
	}

	public Classe(String annoScolastico, String aulaNum, String classeAnno, String piano, String sezione,
			List<Studente> studentes) {
		super();
		this.annoScolastico = annoScolastico;
		this.aulaNum = aulaNum;
		this.classeAnno = classeAnno;
		this.piano = piano;
		this.sezione = sezione;
		this.studentes = studentes;
	}



	public Classe(Integer id, String annoScolastico, String aulaNum, String classeAnno, String piano, String sezione,
			List<Studente> studentes) {
		super();
		this.id = id;
		this.annoScolastico = annoScolastico;
		this.aulaNum = aulaNum;
		this.classeAnno = classeAnno;
		this.piano = piano;
		this.sezione = sezione;
		this.studentes = studentes;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAnnoScolastico() {
		return this.annoScolastico;
	}

	public void setAnnoScolastico(String annoScolastico) {
		this.annoScolastico = annoScolastico;
	}

	public String getAulaNum() {
		return this.aulaNum;
	}

	public void setAulaNum(String aulaNum) {
		this.aulaNum = aulaNum;
	}

	public String getClasseAnno() {
		return this.classeAnno;
	}

	public void setClasseAnno(String classeAnno) {
		this.classeAnno = classeAnno;
	}

	public String getPiano() {
		return this.piano;
	}

	public void setPiano(String piano) {
		this.piano = piano;
	}

	public String getSezione() {
		return this.sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}

	public List<Studente> getStudentes() {
		return this.studentes;
	}

	public void setStudentes(List<Studente> studentes) {
		this.studentes = studentes;
	}

	public Studente addStudente(Studente studente) {
		getStudentes().add(studente);
		studente.setClasse(this);

		return studente;
	}

	public Studente removeStudente(Studente studente) {
		getStudentes().remove(studente);
		studente.setClasse(null);

		return studente;
	}

}