package it.preventivo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.preventivo.entity.VerificationToken;
import it.preventivo.repository.VerificationTokenRepository;

import java.util.Date;

@Service
public class VerificationTokenService {

	@Autowired
	private VerificationTokenRepository tokenRepository;

	public boolean verifyToken(String email, String token) {
		VerificationToken verificationToken = tokenRepository.findByEmail(email);
		System.out.println("STAMPO Verification token: " + verificationToken);
		/**
		 * verificationToken != null: Questa parte verifica se l'oggetto
		 * verificationToken esiste. Se verificationToken è null, significa che non
		 * esiste un token di verifica associato (potrebbe essere che l'email non ha un
		 * token valido). verificationToken.getToken().equals(token): Qui si verifica se
		 * il token ottenuto dall'oggetto verificationToken è uguale al token passato
		 * come argomento (token). Questo è un controllo per verificare se il token
		 * fornito dall'utente corrisponde a quello salvato nel database.
		 */
		if (verificationToken != null && verificationToken.getToken().equals(token)) {
			// Verifica se il token è scaduto
			/**
			 * Se il primo if è vero (cioè il token esiste e corrisponde), entriamo in
			 * questo secondo blocco. verificationToken.getExpiryDate(): Questo metodo
			 * restituisce la data di scadenza del token di verifica. .after(new Date()):
			 * Qui stiamo confrontando la data di scadenza con la data e ora attuali (new
			 * Date()). Se la data di scadenza è successiva alla data attuale, significa che
			 * il token è ancora valido (non è scaduto).
			 */
			if (verificationToken.getExpiryDate().after(new Date())) {
				// Se il token non è scaduto, lo eliminiamo dal database
				tokenRepository.delete(verificationToken);
				System.out.println("Il token non è scaduto e restituisco : "+true);
				return true;
			}
		}
		System.out.println("Il token è scaduto e restituisco : "+false);
		return false;
	}
}
