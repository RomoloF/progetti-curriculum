package it.romolo.servizziRestFul;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.spring
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.romolo.entity.Studente;
import it.romolo.repository.ClasseRepository;
import it.romolo.repository.StudenteRepository;

@RestController
@RequestMapping("/api/studenti")
public class StudenteController {

    @Autowired
    private StudenteRepository studenteRepository;

    @Autowired
    private ClasseRepository classeRepository;

    private Logger logger=LoggerFactory.getLogger(StudenteController.class);


    @GetMapping
    public List<Studente> getAllStudenti() {
    	System.out.println("CIAO ROMOLO TI STO RESTITUENDO UNA LISTA DI STUDENTI");
        return studenteRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Studente> getStudenteById(@PathVariable Integer id) {
        Optional<Studente> studente = studenteRepository.findById(id);
        System.out.println("CIAO ROMOLO TI STO RESTITUENDO LO STUDENTE CON ID"+id);
        return studente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/cercaPerCognome/{cognome}")
    public List<Studente> searchStudenti(@RequestParam(required = false) String cognome) {
    	System.out.println("CIAO ROMOLO TI STO RESTITUENDO UNA LISTA DI STUDENTI PER COGNOME");
        if (cognome != null && !cognome.isEmpty()) {
        	System.out.println(cognome);
            return studenteRepository.getStudentiByCognome(cognome);
        } else {
            // Se nessun parametro di ricerca è specificato, restituisci tutti gli studenti
            return studenteRepository.findAll();
        }
    }

    @GetMapping("/cercaPernome/{nome}")
   public List<Studente> searchStudentinome(@RequestParam(required = false) String nome) {
    	System.out.println("CIAO ROMOLO TI STO RESTITUENDO UNA LISTA DI STUDENTI PER NOME questo è il SERVER 8085");
      if (nome != null && !nome.isEmpty()) {
      	System.out.println(nome);
           return studenteRepository.getStudentiByNome(nome);
       } else {
           // Se nessun parametro di ricerca è specificato, restituisci tutti gli studenti
            return studenteRepository.findAll();
        }
    }

    @GetMapping("/cercaPerEmail/{email}")
    public List<Studente> searchStudentiemail(@RequestParam(required = false) String email) {
        if (email != null && !email.isEmpty()) {
        	System.out.println(email);
           return studenteRepository.getStudentiByEmail(email);
        } else {
            // Se nessun parametro di ricerca è specificato, restituisci tutti gli studenti
            return studenteRepository.findAll();
        }
    }

//							    // Metodo per ottenere tutti gli studenti di una specifica classe usando l'oggetto Classe
//							    public List<Studente> getStudentiPerClasse(Classe classe) {
//							    	Classe classe = classeRepository; // Ottieni o crea l'oggetto Classe
//							        return studenteRepository.findByClasse(classe);
//							    }
//
//							    // Metodo per ottenere tutti gli studenti di una specifica classe usando l'ID della Classe
//							    public List<Studente> getStudentiPerClasseId(Integer classeId) {
//							    	Classe classe=classeRepository.getById(classeId);
//							    	System.out.println(classe+"    Questa dovrebbe essere la classe ");
//							    	//Integer classeId = classe.getId(); // Ottieni l'ID della classe
//							        return studenteRepository.findByClasseId(classeId);
//							    }
//


    @PostMapping
    public Studente createStudente(@RequestBody Studente studente) {
        return studenteRepository.save(studente);
    }

    @PutMapping("/aggiorna/{id}")
    public ResponseEntity<Studente> updateStudente(@PathVariable Integer id, @RequestBody Studente studenteDetails) {
        Optional<Studente> optionalStudente = studenteRepository.findById(id);
        if (optionalStudente.isPresent()) {
            Studente studente = optionalStudente.get();
            studente.setNome(studenteDetails.getNome());
            studente.setCognome(studenteDetails.getCognome());
            studente.setEmail(studenteDetails.getEmail());
            studente.setTelefono(studenteDetails.getTelefono());
            studente.setRifGenitore(studenteDetails.getRifGenitore());
            studente.setClasse(studenteDetails.getClasse());
            return ResponseEntity.ok(studenteRepository.save(studente));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
//    public void deleteById(Long id) {
//        utenteRepository.deleteById(id);
//    }

    @DeleteMapping("/cancella/{id}")
    public ResponseEntity<Void> deleteStudente(@PathVariable Integer id) {
        try {
            Optional<Studente> studente = studenteRepository.findById(id);
                logger.info("Richiesta di cancellazione ricevuta per studente con id:{}",id);

            if (studente.isPresent()) {
                logger.info("Ricerca lo studente e se è presente   con id:{}",id);

                studenteRepository.delete(studente.get());
                logger.info("Studente con id {} cancellato con successo.", id);
                return ResponseEntity.noContent().build();
            } else {
                logger.warn("Studente con id {} non trovato.", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
                logger.error("Errore durante la cancellazione dello studente con id {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    }

