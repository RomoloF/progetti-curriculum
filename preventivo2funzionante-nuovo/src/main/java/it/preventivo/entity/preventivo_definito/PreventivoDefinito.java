package it.preventivo.entity.preventivo_definito;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * Entità che rappresenta un Preventivo Definito.
 */
@Entity
@Table(name = "preventivo_definito")
public class PreventivoDefinito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_preventivo", nullable = true)
    private String nomePreventivo;

    @Column(name = "cliente_id", nullable = true)
    private Long clienteId;

    @Column(name = "tipo_lavoro")
    private String tipoLavoro;

    @Column(name = "descrizione")
    private String descrizione;

    @Column(name = "prezzo_totale_preventivo")
    private double prezzoTotalePreventivo;

    @Column(name = "data_creazione")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCreazione;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomePreventivo() {
        return nomePreventivo;
    }

    public void setNomePreventivo(String nomePreventivo) {
        this.nomePreventivo = nomePreventivo;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getTipoLavoro() {
        return tipoLavoro;
    }

    public void setTipoLavoro(String tipoLavoro) {
        this.tipoLavoro = tipoLavoro;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public double getPrezzoTotalePreventivo() {
        return prezzoTotalePreventivo;
    }

    public void setPrezzoTotalePreventivo(double prezzoTotalePreventivo) {
        this.prezzoTotalePreventivo = prezzoTotalePreventivo;
    }

    public Date getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(Date dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

	public PreventivoDefinito(String nomePreventivo, Long clienteId, String tipoLavoro, String descrizione,
			double prezzoTotalePreventivo, Date dataCreazione) {
		super();
		this.nomePreventivo = nomePreventivo;
		this.clienteId = clienteId;
		this.tipoLavoro = tipoLavoro;
		this.descrizione = descrizione;
		this.prezzoTotalePreventivo = prezzoTotalePreventivo;
		this.dataCreazione = dataCreazione;
	}

	public PreventivoDefinito() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "PreventivoDefinito [id=" + id + ", nomePreventivo=" + nomePreventivo + ", clienteId=" + clienteId
				+ ", tipoLavoro=" + tipoLavoro + ", descrizione=" + descrizione + ", prezzoTotalePreventivo="
				+ prezzoTotalePreventivo + ", dataCreazione=" + dataCreazione + "]";
	}
    
}

