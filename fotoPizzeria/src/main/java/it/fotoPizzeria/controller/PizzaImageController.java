package it.fotoPizzeria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.fotoPizzeria.service.PizzaImageDownloaderService;

import java.io.IOException;

@Controller
public class PizzaImageController {

    @Autowired
    private PizzaImageDownloaderService pizzaImageDownloaderService;

    /**
     * Mostra la pagina per scaricare le immagini delle pizze.
     * 
     * @return Il nome del template Thymeleaf
     */
    @GetMapping("/")
    public String showDownloadPage() {
        return "downloadImages"; // Nome del template Thymeleaf
    }

    /**
     * Scarica le immagini delle pizze dal sito web e mostra un messaggio di risultato.
     * 
     * @param url URL del sito web
     * @param downloadDir Cartella di destinazione per le immagini
     * @param model Modello per passare i dati alla vista
     * @return Il nome del template Thymeleaf
     */
    @GetMapping("/download-pizza-images")
    public String downloadPizzaImages(@RequestParam String url, 
                                      @RequestParam String downloadDir, 
                                      Model model) {
        try {
            pizzaImageDownloaderService.downloadPizzaImages(url, downloadDir);
            model.addAttribute("message", "Download completato con successo!");
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "Errore durante il download: " + e.getMessage());
        }
        return "downloadImages"; // Torna alla stessa pagina mostrando il messaggio
    }
}
