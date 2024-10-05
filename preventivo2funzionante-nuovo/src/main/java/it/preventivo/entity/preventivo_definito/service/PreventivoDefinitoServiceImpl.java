package it.preventivo.entity.preventivo_definito.service;

import it.preventivo.entity.preventivo_definito.PreventivoDefinito;
import it.preventivo.entity.preventivo_definito.repository.PreventivoDefinitoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementazione del servizio per la gestione dei Preventivi Definiti.
 */
@Service
public class PreventivoDefinitoServiceImpl implements PreventivoDefinitoService {

    @Autowired
    private PreventivoDefinitoRepository repository;

    @Override
    public PreventivoDefinito salvaPreventivo(PreventivoDefinito preventivo) {
        return repository.save(preventivo);
    }

    @Override
    public List<PreventivoDefinito> trovaTutti() {
        return repository.findAll();
    }

    @Override
    public PreventivoDefinito trovaPerId(Long id) {
        Optional<PreventivoDefinito> preventivo = repository.findById(id);
        return preventivo.orElse(null);
    }

    @Override
    public void cancellaPreventivo(Long id) {
        repository.deleteById(id);
    }

    @Override
    public PreventivoDefinito aggiornaPreventivo(Long id, PreventivoDefinito preventivo) {
        if (repository.existsById(id)) {
            preventivo.setId(id);
            return repository.save(preventivo);
        }
        return null;
    }
    /**
     * Trova i preventivi per un dato utente.
     * @param idUtente ID dell'utente.
     * @return Lista di preventivi associati all'utente.
     */
    public List<PreventivoDefinito> trovaPreventiviPerUtente(Long idUtente) {
        return repository.findByClienteId(idUtente);
    }
}