package it.login.securingweb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
/**
 * La WebSecurityConfigclasse è annotata con @EnableWebSecurityper abilitare il
 * supporto di sicurezza web di Spring Security e fornire l'integrazione di
 * Spring MVC. Espone inoltre due bean per impostare alcune specifiche per la
 * configurazione di sicurezza web:
 * 
 * Il SecurityFilterChainbean definisce quali percorsi URL devono essere
 * protetti e quali no. In particolare, i percorsi /e /home sono configurati per
 * non richiedere alcuna autenticazione. Tutti gli altri percorsi devono essere
 * autenticati.
 * 
 * Quando un utente effettua correttamente l'accesso, viene reindirizzato alla
 * pagina richiesta in precedenza che richiedeva l'autenticazione. Esiste una
 * /login pagina personalizzata (specificata da loginPage()), e chiunque può
 * visualizzarla.
 * 
 * Il UserDetailsServicebean imposta un archivio utente in memoria con un
 * singolo utente. A tale utente viene assegnato un nome utente user, una
 * password password e un ruolo USER.
 * 
 * Ora devi creare la pagina di login. Esiste già un view controller per la
 * loginview, quindi devi solo creare la login view stessa, come
 * src/main/resources/templates/login.html mostra il seguente elenco (da ):
 */
public class WebSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	 http
         .authorizeRequests()
             .requestMatchers("/admin").hasRole("ADMIN")  // Solo admin può accedere a questa URL
             .requestMatchers("/user").hasRole("USER")    // Solo user può accedere a questa URL
             .requestMatchers("/anonimo").permitAll()     // Accesso per tutti
             .anyRequest().authenticated()              // Altre richieste devono essere autenticate
             .and()
         .formLogin()
             .loginPage("/login")                       // La tua pagina di login personalizzata
             .successHandler(new CustomAuthenticationSuccessHandler()) // Imposta il gestore di successo
             .permitAll()
             .and()
         .logout()
             .permitAll();
 
		return http.build();
	}

    @Bean
    UserDetailsService userDetailsService() {
		UserDetails user = User.builder()
		        .username("user")
		        .password(passwordEncoder().encode("password"))  // Usa un encoder esplicito
		        .roles("USER")// Ruolo user
		        .build();
		UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))  // Password per l'admin
                .roles("ADMIN", "USER")  // Ruolo ADMIN e USER
                .build();
		UserDetails anonimo = User.builder()
                .username("anonimo")
                .password(passwordEncoder().encode("anonimo"))  // Password per l'anonimo
                .roles("anonimo")  // Ruolo anonimo
                .build();
		
		// Aggiungi tutti gli utenti al gestore in memoria
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(user);
        manager.createUser(admin);
        manager.createUser(anonimo);
        
        return manager; // Restituisce il gestore con tutti gli utenti
    }

		    
    /**
     * Bean per l'encoder delle password.
     * 
     * @return PasswordEncoder
     */
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();  // Usa BCrypt per codificare le password
	}
	
}