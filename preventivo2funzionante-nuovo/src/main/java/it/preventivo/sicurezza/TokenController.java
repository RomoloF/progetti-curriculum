package it.preventivo.sicurezza;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

import javax.crypto.SecretKey;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
@RestController
public class TokenController {

    //private final String secretKey = "mySecretKey"; // Deve essere la stessa chiave usata per firmare il token
    /**
	 * Alternativa: Chiave segreta personalizzata Se vuoi utilizzare una chiave
	 * personalizzata, devi assicurarti che sia almeno di 512 bit (64 caratteri, se
	 * è una stringa). Ecco un esempio di chiave di 512 bit:
	 */
    //String secretKey = "thisIsA512bitKeyExampleThatIsAtLeast64CharactersLongToBeSecure";
  //  private final String secretKey = "bJx8G9A42vS6pL8QyW5kMn1Zr7cUxK2HjI4dRq8WbP9lX0vKkY3wSpT6QhY7Nz8D";
 // Usa la stessa chiave condivisa per la verifica
    private static final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    
    @GetMapping("/verify")
    public String verifyToken(@RequestParam("token") String token) {
    	
        try {
            // Decodifica il token JWT e ottieni i claims
            Claims claims = Jwts.parser()
                .setSigningKey(secretKey)  // Verifica con la chiave segreta
                .parseClaimsJws(token)
                .getBody();

            // Estrai le informazioni dal token
            String username = claims.getSubject();
            String password = claims.get("password", String.class);
            String otp = claims.get("otp", String.class);

            // Stampa o logga i dati (solo per debug)
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);
            System.out.println("OTP: " + otp);

            // Aggiungi qui la logica per verificare l'utente nel database
            // Ad esempio:
            boolean userExists = checkUserInDatabase(username, password);
            boolean otpValid = checkOtp(username, otp);

            // Se l'utente esiste e l'OTP è valido, prosegui
            if (userExists && otpValid) {
                // Logica per la verifica dell'email o altre azioni
                return "Utente verificato con successo!";
            } else {
                return "Verifica fallita: utente o OTP non validi.";
            }

        } catch (SignatureException e) {
            // Gestisci errori relativi alla firma del token JWT
            return "Token JWT non valido o firma errata!";
        } catch (Exception e) {
            // Gestione di altri errori
            return "Errore durante la verifica del token!";
        }
    }

    // Simula la verifica dell'utente nel database (qui dovresti implementare la tua logica)
    private boolean checkUserInDatabase(String username, String password) {
        // Verifica se l'utente con la combinazione di username e password esiste nel database
        // Esempio: chiama un servizio che interroga il DB
        // return userService.isValidUser(username, password);
        return "testUser".equals(username) && "testPassword".equals(password);
    }

    // Simula la verifica dell'OTP (One-Time Password)
    private boolean checkOtp(String username, String otp) {
        // Verifica l'OTP associato all'utente, ad esempio potresti memorizzare l'OTP in sessione o in cache
        // return otpService.isValidOtpForUser(username, otp);
        return "1234".equals(otp); // Simulazione di un OTP corretto
    }
}
