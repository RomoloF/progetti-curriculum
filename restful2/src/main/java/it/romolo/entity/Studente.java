package it.romolo.entity;
import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;





@Entity
@Table(name="studente")
@NamedQuery(name="Studente.findAll", query="SELECT s FROM Studente s")
public class Studente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	//Associazione biderizionale
	@ManyToOne
	@JoinColumn(name="id_classe", referencedColumnName = "id" )

	private Classe classe;

	private String nome;

	private String cognome;

	private String telefono;

	private String email;

	@Column(name="rif_genitore")
	private String rifGenitore;

// COSTRUTTORE DI DEFAULT
	public Studente() {
	}


	public Studente(Classe classe, String nome, String cognome, String telefono, String email, String rifGenitore) {
		super();
		this.classe = classe;
		this.nome = nome;
		this.cognome = cognome;
		this.telefono = telefono;
		this.email = email;
		this.rifGenitore = rifGenitore;
	}


	public Studente(int id, Classe classe, String nome, String cognome, String telefono, String email,
			String rifGenitore) {
		super();

		this.id = id;
		this.classe = classe;
		this.nome = nome;
		this.cognome = cognome;
		this.telefono = telefono;
		this.email = email;
		this.rifGenitore = rifGenitore;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRifGenitore() {
		return this.rifGenitore;
	}

	public void setRifGenitore(String rifGenitore) {
		this.rifGenitore = rifGenitore;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Classe getClasse() {
		return this.classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}

}