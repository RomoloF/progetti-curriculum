package it.login.securingweb;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Gestore di successo per l'autenticazione.
 * Reindirizza gli utenti a diverse pagine in base ai loro ruoli.
 */
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, 
                                        HttpServletResponse response, 
                                        Authentication authentication) throws IOException {
        String redirectUrl;

        // Controlla i ruoli dell'utente autenticato
        if (authentication.getAuthorities().stream().anyMatch(grantedAuthority -> 
                grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
        	//Reindirizza gli utenti a diverse pagine in base ai loro ruoli.
            redirectUrl = "/admin"; // URL per l'admin
        } else if (authentication.getAuthorities().stream().anyMatch(grantedAuthority -> 
                grantedAuthority.getAuthority().equals("ROLE_USER"))) {
        	//Reindirizza gli utenti a diverse pagine in base ai loro ruoli.
            redirectUrl = "/user"; // URL per l'utente
        } else {
        	//Reindirizza gli utenti a diverse pagine in base ai loro ruoli.
            redirectUrl = "/anonimo"; // URL per l'anonimo
        }

        // Reindirizza l'utente all'URL appropriato
        response.sendRedirect(redirectUrl);
    }
}
