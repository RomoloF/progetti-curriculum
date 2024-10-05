package it.login.securingweb;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
	/**
	 * Il addViewControllers()metodo (che sovrascrive il metodo con lo stesso nome
	 * in WebMvcConfigurer) aggiunge quattro controller di visualizzazione. Due dei
	 * controller di visualizzazione fanno riferimento alla visualizzazione il cui
	 * nome è home(definito in home.html), e un altro fa riferimento alla
	 * visualizzazione denominata hello(definita in hello.html). Il quarto
	 * controller di visualizzazione fa riferimento a un'altra visualizzazione
	 * denominata login. Creerai tale visualizzazione nella prossima sezione.
	 * 
	 * A questo punto, puoi passare direttamente a " Esegui l'applicazione " ed
	 * eseguire l'applicazione senza dover effettuare l'accesso a nulla.
	 * 
	 * Ora che hai un'applicazione web non protetta, puoi renderla più sicura.
	 */
	 /**
     * Metodo per aggiungere controller di visualizzazione.
     * Questo metodo mappa le seguenti URL alle rispettive visualizzazioni:
     * - "/" e "/home" mappano alla visualizzazione "home".
     * - "/hello" mappa alla visualizzazione "hello".
     * - "/login" mappa alla visualizzazione "login".
     * 
     * Puoi creare i file HTML corrispondenti per queste visualizzazioni nella cartella
     * dei template (es. src/main/resources/templates/).
     * 
     * @param registry il registro dei controller di visualizzazione
     */
	@Override
        // Aggiunge le route per le visualizzazioni, con le rispettive viewNames.
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/home").setViewName("home");
		registry.addViewController("/").setViewName("home");
		registry.addViewController("/hello").setViewName("hello");
		registry.addViewController("/login").setViewName("login");
	}

}