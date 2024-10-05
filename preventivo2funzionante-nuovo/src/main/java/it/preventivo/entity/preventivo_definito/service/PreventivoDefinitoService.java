package it.preventivo.entity.preventivo_definito.service;

import it.preventivo.entity.preventivo_definito.PreventivoDefinito;
import java.util.List;

/**
 * Interfaccia per il servizio di gestione dei Preventivi Definiti.
 */
public interface PreventivoDefinitoService {
    PreventivoDefinito salvaPreventivo(PreventivoDefinito preventivo);
    List<PreventivoDefinito> trovaTutti();
    PreventivoDefinito trovaPerId(Long id);
    void cancellaPreventivo(Long id);
    PreventivoDefinito aggiornaPreventivo(Long id, PreventivoDefinito preventivo);
	List<PreventivoDefinito> trovaPreventiviPerUtente(Long idUtente);
}