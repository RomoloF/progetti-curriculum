package it.resina.controller.servizzi;

import org.springframework.stereotype.Service;

@Service
public class CalcolatoreService {

    private static final double DENSITA_RESINA = 1.15;  // g/ml
    private static final double DENSITA_INDURENTE = 1.0;  // g/ml

    /**
     * Metodo per calcolare la quantità di resina (base) e indurente in base alle dimensioni della superficie e al rapporto fornito.
     *
     * @param lunghezza   Lunghezza della superficie in centimetri (cm)
     * @param larghezza   Larghezza della superficie in centimetri (cm)
     * @param altezza     Altezza della superficie in millimetri (mm)
     * @param rapportoResina    Numero di parti di resina (base) nel rapporto
     * @param rapportoIndurente Numero di parti di indurente nel rapporto
     * @return Array contenente [quantità base ml, base grammi, indurente ml, indurente grammi, totale ml, totale grammi]
     */
    public double[] calcolaResina(double lunghezza, double larghezza, double altezzaCM, int rapportoResina, int rapportoIndurente) {
        // Converti lunghezza e larghezza in millimetri per ottenere il volume in mm³
        double lunghezzaMM = lunghezza * 10; // da cm a mm
        double larghezzaMM = larghezza * 10; // da cm a mm
        double altezzaMM = altezzaCM * 10; // da cm a mm
        
        // Calcola il volume in mm³ (lunghezza * larghezza * altezza)
        double volumeMM = lunghezzaMM * larghezzaMM * altezzaMM;

        // Converti il volume in millilitri (1 mm³ = 0.001 ml)
        double volumeML = volumeMM * 0.001;

        // Calcolo delle proporzioni in base al rapporto fornito
        double rapportoTotale = rapportoResina + rapportoIndurente;
        
        // Resina = volume totale * (rapporto della resina / rapporto totale)
        double baseML = volumeML * (rapportoResina / rapportoTotale);
        
        // Indurente = volume totale * (rapporto dell'indurente / rapporto totale)
        double indurenteML = volumeML * (rapportoIndurente / rapportoTotale);

        // Conversione in grammi usando la densità
        double baseGrammi = baseML * DENSITA_RESINA;
        double indurenteGrammi = indurenteML * DENSITA_INDURENTE;

        // Calcolo totale
        double totaleML = baseML + indurenteML;
        double totaleGrammi = baseGrammi + indurenteGrammi;

        return new double[] { baseML, baseGrammi, indurenteML, indurenteGrammi, totaleML, totaleGrammi };
    }

}

