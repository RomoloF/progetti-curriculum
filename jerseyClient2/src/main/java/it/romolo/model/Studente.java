package it.romolo.model;
import java.io.Serializable;

public class Studente implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private Classe classe;

	private String nome;

	private String cognome;

	private String telefono;

	private String email;

	private String rifGenitore;

	private String id_classe;

	private int  ref_classe;

	public int getRef_classe() {
		return ref_classe;
	}

	public void setRef_classe(int ref_classe) {
		this.ref_classe = ref_classe;
	}

	public String getId_classe() {
		return id_classe;
	}

	public void setId_classe(String id_classe) {
		this.id_classe = id_classe;
	}

	public Studente() {
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

	public Studente( String nome, String cognome, String telefono, String email,
			String rifGenitore, String id_classe, int ref_classe) {
		super();
		this.id = id;
		this.classe = classe;
		this.nome = nome;
		this.cognome = cognome;
		this.telefono = telefono;
		this.email = email;
		this.rifGenitore = rifGenitore;
		this.id_classe = id_classe;
		this.ref_classe = ref_classe;
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

	@Override
	public String toString() {
		return "Studente [id=" + id + ", classe=" + classe + ", nome=" + nome + ", cognome=" + cognome + ", telefono="
				+ telefono + ", email=" + email + ", rifGenitore=" + rifGenitore + "]";
	}

}