package it.fotoPizzeria.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Set;

@Service
public class PizzaImageDownloaderService {

    /**
     * Scarica tutte le immagini da un sito web di pizzerie e le salva in una cartella locale.
     * 
     * @param websiteUrl URL della pizzeria
     * @param downloadDirectory Cartella di destinazione per le immagini
     * @throws IOException Se si verifica un errore durante il download
     */
	public void downloadPizzaImages(String websiteUrl, String downloadDirectory) throws IOException {
	    // Estrai il contenuto HTML dal sito web
	    Document doc = Jsoup.connect(websiteUrl).get();
	    
	    // Seleziona tutti i tag <img> e i tag con stile background-image
	    Elements imageElements = doc.select("img, [style*=background-image]");

	    // Crea la cartella se non esiste
	    Path downloadPath = Paths.get(downloadDirectory);
	    System.out.println(downloadPath.toString());
	    if (!Files.exists(downloadPath)) {
	        Files.createDirectories(downloadPath);
	    }

	    // Inizializza il contatore per numerare le immagini
	    int count = 1;

	    // Insieme per tenere traccia degli URL già scaricati
	    Set<String> downloadedUrls = new HashSet<>();

	    // Itera su ogni immagine trovata
	    for (Element img : imageElements) {
	        String imageUrl = null;

	        // Caso 1: Immagini con attributo 'srcset'
	        if (img.hasAttr("srcset")) {
	            String[] sources = img.attr("srcset").split(",");
	            imageUrl = sources[sources.length - 1].split(" ")[0]; // Prendi l'ultimo URL (risoluzione più alta)
	        }
	        // Caso 2: Immagini con attributo 'src'
	        else if (img.hasAttr("src")) {
	            imageUrl = img.absUrl("src");
	        }
	        // Caso 3: Immagini con 'background-image' nello stile
	        else if (img.hasAttr("style") && img.attr("style").contains("background-image")) {
	            String style = img.attr("style");
	            int startIndex = style.indexOf("url('") + 5;
	            int endIndex = style.indexOf("')", startIndex);
	            imageUrl = style.substring(startIndex, endIndex);
	        }

	        // Verifica se l'URL è valido e non duplicato
	        if (imageUrl != null && !downloadedUrls.contains(imageUrl) && (imageUrl.endsWith(".jpg") || imageUrl.endsWith(".png"))) {
	            // Scarica e salva l'immagine con il contatore
	            downloadImage(imageUrl, downloadDirectory, count);
	            downloadedUrls.add(imageUrl); // Aggiungi l'URL all'insieme
	            count++; // Incrementa il contatore
	        }
	    }
	}

	private void downloadImage(String imageUrl, String downloadDirectory, int count) throws IOException {
	    URL url = new URL(imageUrl);
	    try (InputStream in = url.openStream()) {
	        // Estrai l'estensione del file (es. .jpg, .png)
	        String fileExtension = imageUrl.substring(imageUrl.lastIndexOf("."));
	        
	        // Genera un nome univoco per l'immagine, ad esempio pizza_1.jpg, pizza_2.jpg
	        String fileName = "pizza_" + count + fileExtension;
	        
	        // Costruisci il percorso del file
	        Path outputPath = Paths.get(downloadDirectory, fileName);
	        
	        // Salva l'immagine
	        Files.copy(in, outputPath, StandardCopyOption.REPLACE_EXISTING);
	        System.out.println("Immagine scaricata: " + outputPath);
	    }
	}

}
