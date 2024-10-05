package it.preventivo.entity;

import org.springframework.stereotype.Controller;

/**
 * La classe LavoriSelezionati rappresenta un lavoro con le sue proprietà
 * e calcola automaticamente il prezzo totale in base al prezzo unitario e alla quantità selezionata.
 */
@Controller
public class LavoriSelezionati {

    private int id;
    private String descrizione;
    private double prezzoUnitario;
    private int quantitaSelezionate;
    private double prezzoTotale;
    private Long clienteId;
    private String tipoLavoro;
    

    

    public LavoriSelezionati() {
		super();
	}
       
	public LavoriSelezionati(int id, String descrizione, double prezzoUnitario, int quantitaSelezionate,
			double prezzoTotale, Long clienteId, String tipoLavoro) {
		super();
		this.id = id;
		this.descrizione = descrizione;
		this.prezzoUnitario = prezzoUnitario;
		this.quantitaSelezionate = quantitaSelezionate;
		this.prezzoTotale = prezzoTotale;
		this.clienteId = clienteId;
		this.tipoLavoro = tipoLavoro;
	}
	
	public LavoriSelezionati(String descrizione, double prezzoUnitario, int quantitaSelezionate, double prezzoTotale,
			Long clienteId, String tipoLavoro) {
		super();
		this.descrizione = descrizione;
		this.prezzoUnitario = prezzoUnitario;
		this.quantitaSelezionate = quantitaSelezionate;
		this.prezzoTotale = prezzoTotale;
		this.clienteId = clienteId;
		this.tipoLavoro = tipoLavoro;
	}


	@Override
	public String toString() {
		return "LavoriSelezionati [id=" + id + ", descrizione=" + descrizione + ", prezzoUnitario=" + prezzoUnitario
				+ ", quantitaSelezionate=" + quantitaSelezionate + ", prezzoTotale=" + prezzoTotale + ", clienteId="
				+ clienteId + ", tipoLavoro=" + tipoLavoro + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public double getPrezzoUnitario() {
		return prezzoUnitario;
	}

	public void setPrezzoUnitario(double prezzoUnitario) {
		this.prezzoUnitario = prezzoUnitario;
	}

	public int getQuantitaSelezionate() {
		return quantitaSelezionate;
	}

	public void setQuantitaSelezionate(int quantitaSelezionate) {
		this.quantitaSelezionate = quantitaSelezionate;
	}

	public double getPrezzoTotale() {
		return prezzoTotale;
	}

	public void setPrezzoTotale(double prezzoTotale) {
		this.prezzoTotale = prezzoTotale;
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

	

	
}
