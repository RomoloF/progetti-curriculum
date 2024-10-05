package it.romolo.model;
import java.io.Serializable;
import java.util.List;

public class Classe implements Serializable {
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String annoScolastico;

	private String aulaNum;

	private String classeAnno;

	private String piano;

	private String sezione;

	private List<Studente> studentes;

	public Classe() {
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

	@Override
	public String toString() {
		return "Classe [id=" + id + ", annoScolastico=" + annoScolastico + ", aulaNum=" + aulaNum + ", classeAnno="
				+ classeAnno + ", piano=" + piano + ", sezione=" + sezione + ", studentes=" + studentes + "]";
	}

}