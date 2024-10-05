package it.preventivo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import it.preventivo.entity.VerificationToken;
import it.preventivo.repository.VerificationTokenRepository;

import java.util.Date;
import java.util.Random;

@Service
public class EmailVerificationService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private VerificationToken verificationToken ;
    
    
    // Funzione per generare un codice di verifica
    public String generateVerificationCode() {
        int code = new Random().nextInt(999999);
        
        return String.format("%06d", code); // codice di 6 cifre
    }

    // Funzione per inviare il codice di verifica via email
    /**
     * Invia un'email con un codice di verifica all'indirizzo specificato.
     * 
     * Questo metodo genera un codice di verifica, compone un'email con il codice
     * e la invia all'indirizzo email specificato. Inoltre, il codice di verifica
     * viene stampato nella console per scopi di debug.
     * 
     * @param toEmail l'indirizzo email del destinatario a cui inviare il codice di verifica
     * @param model 
     */
    public void sendVerificationEmail(String toEmail, Model model) {
    	long startTime = System.currentTimeMillis();
    	// Genera un codice di verifica e lo invia via email
        String verificationCode = generateVerificationCode();
        // Stampa il codice di verifica nella console per scopi di debug
        System.out.println("Stampo l'email :"+toEmail);
        System.out.println("Stampo il codice inviato :"+verificationCode);
        // Componi e invia l'email con il codice di verifica
        SimpleMailMessage message = new SimpleMailMessage();
        // Imposta l'indirizzo email del mittente (deve corrispondere a quello configurato per l'autenticazione)
        message.setFrom("r.fiorenza@tiscalinet.it"); // Deve corrispondere al tuo indirizzo di autenticazione
        // Imposta l'indirizzo email del destinatario (quello passato come parametro)
        message.setTo(toEmail);
        // Imposta il titolo e il corpo della email (contengono il codice di verifica)
        message.setSubject("Codice di Verifica");
        // Imposta il corpo della email (contengono il codice di verifica)
        message.setText("Il tuo codice di verifica è: " + verificationCode);
        model.addAttribute("verificationCode", verificationCode);
        // Invia l'email con il codice di verifica'
        System.out.println("Sto inviando il codice di verifica per email ed è >>> "+verificationCode);
        // Stampa un messaggio di conferma
        System.out.println("Email inviata con successo!");
        mailSender.send(message);
        long endTime = System.currentTimeMillis();
        System.out.println("Tempo di invio email: " + (endTime - startTime) + " ms");
        

        // Salvare il codice di verifica e l'email nel database
        // per la verifica successiva
        verificationToken.setEmail(toEmail);
        verificationToken.setToken(verificationCode);
        verificationToken.setExpiryDate(calculateExpiryDate()); // Calcola la data di scadenza del token
        verificationTokenRepository.save(verificationToken);
    }

    private Date calculateExpiryDate() {
        // Definire il periodo di validità del token (esempio: 15 minuti)
        final int EXPIRATION_TIME_IN_MINUTES = 60;
        
        // Ottenere l'orario attuale
        Date now = new Date();
        
        // Calcolare la data di scadenza aggiungendo i minuti definiti al tempo corrente
        long expiryTime = now.getTime() + (EXPIRATION_TIME_IN_MINUTES * 60 * 1000);
        
        // Restituire la data di scadenza
        return new Date(expiryTime);
    }
}
