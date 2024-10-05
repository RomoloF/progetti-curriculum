package it.preventivo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import it.preventivo.service.EmailVerificationService;
import it.preventivo.service.VerificationTokenService;

@Controller
@RequestMapping("/api")
public class VerificationController {

    @Autowired
    private EmailVerificationService emailVerificationService;

    @Autowired
    private VerificationTokenService verificationTokenService;
    
    String verificationCode;
    
    // Mostra la vista di registrazione
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        return "register"; // Nome della vista Thymeleaf per la registrazione
    }
    
    @PostMapping("/send-verification-email")
    public String sendVerificationEmail(@RequestParam String email, Model model) {
         //verificationCode = emailVerificationService.generateVerificationCode(); // Genera il codice di verifica
        emailVerificationService.sendVerificationEmail(email,model);
        model.addAttribute("message", "Codice di verifica inviato. Hai un tempo massimo di 30 minuti per inserire il codice di verifica");
        model.addAttribute("email", email);
       // model.addAttribute("verificationCode", verificationCode);
        System.out.println("Stampo l'email :"+email);
        System.out.println("Stampo il codice inviato :"+model.getAttribute(verificationCode));
        return "verification"; // Nome della vista Thymeleaf da visualizzare
    }

    @PostMapping("/verify")
    public String verifyEmail(@RequestParam String email, @RequestParam String token, Model model) {
        boolean isValid = verificationTokenService.verifyToken(email, token);
        System.out.println("Verification token");
        System.out.println("Email :"+email);
        System.out.println("Token :"+token);
        System.out.println("Token valido? :"+isValid);
        // Se il token è valido, aggiunge la email al database e mostra la vista di successo
        if (isValid) {
            // aggiungi l'email al database
            //...
            model.addAttribute("message", "Email verificata con successo.");
            
            return "success"; // Nome della vista Thymeleaf per la conferma del successo
        } else {
            model.addAttribute("error", "Codice di verifica non valido o scaduto.");
            return "verification"; // Torna alla vista di verifica con un messaggio di errore
        }

    }
}
