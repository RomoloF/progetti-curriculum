package it.resina;

import java.util.Timer;
import java.util.TimerTask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@SpringBootApplication
public class ResinaEpossidicaApplication {
	
	
	 private static Timer timer = new Timer();
		/**
		 * Quindi:
		 * 
		 * 3600000 ms ÷ 1000 = 3600 seconds 3600000 ms÷1000=3600 seconds Pertanto,
		 * 3600000 millisecondi corrispondono a 3600 secondi, che equivale a 1 ora.
		 * 
		 */
	   // private static final long INACTIVITY_TIMEOUT = 3600000; // un ora di inattività
	   // private static final long INACTIVITY_TIMEOUT = 300000; // 5 minuti di inattività
	    private static final long INACTIVITY_TIMEOUT = 60000; // 1 minuti di inattività
	    private static ConfigurableApplicationContext context; // Per salvare il contesto Spring
	

	public static void main(String[] args) {
		context = SpringApplication.run(ResinaEpossidicaApplication.class, args);
		startInactivityMonitor(); // Avvia il monitor dell'inattività
	}
	
	private static void startInactivityMonitor() {
        resetTimer(); // Avvia o resetta il timer all'avvio
    }
	
	// Reset del timer su ogni richiesta HTTP
    private static void resetTimer() {
        timer.cancel();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Timeout di inattività raggiunto. Spegnimento dell'applicazione...");
                shutdownApp(); // Chiamata per spegnere l'applicazione
            }
        }, INACTIVITY_TIMEOUT);
    }
 // Metodo per spegnere l'applicazione e il server
    private static void shutdownApp() {
        SpringApplication.exit(context, () -> 0);  // Chiude il contesto Spring
        System.exit(0);  // Termina il processo JVM
    }
    @Bean
    public HttpSessionListener httpSessionListener() {
        return new HttpSessionListener() {
            @Override
            public void sessionCreated(HttpSessionEvent se) {
                resetTimer(); // Resetta il timer quando viene creata una nuova sessione
            }

            @Override
            public void sessionDestroyed(HttpSessionEvent se) {
                // Non è necessario fare nulla qui per la chiusura dell'app
            }
        };
    }
}
