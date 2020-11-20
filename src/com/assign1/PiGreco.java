package com.assign1;

import java.lang.Thread;

/**
 * @author Davide Chen
 * @version 1.0
 */

public class PiGreco implements Runnable {

    //si è preferito l'utilizzo del metodo dell'implementazione dell'interfaccia Runnable,
    // invece che estendere la classe Thread

    private final double accuracy;
    private final int timeout;

    public PiGreco(double a, int t) {
        this.accuracy = a;
        this.timeout = t;
    }

    public void run() {
        //variabile su cui salvare il valore del π calcolato
        double piValue = 0.0;

        //valore n della sommatoria
        //usato anche come flag di parità al posto della funzione potenza
        int n = 0;

        System.out.println("Calcolo del π in corso, attendere prego....\n");

        // calcolo del π
        // si cicla finché non si ottiene il grado di accurattezza < accuracy &&
        // finché il thread non è stato interrotto
        do {
            if(n%2 == 0) {
                piValue += 4.0 / (2*n + 1);
                n++;
            }
            else {
                piValue -= 4.0 / (2*n + 1);
                n++;
            }
        } while(Math.abs(piValue - Math.PI) > accuracy  &&  !Thread.currentThread().isInterrupted());

        double difference = Math.abs(piValue - Math.PI);

        if(difference < accuracy) {
            System.out.printf("\nTerminazione programma per raggiungimento di grado di accuratezza richiesta di %.15f\n", accuracy);
            System.out.printf("Con grado di accuratezza effettiva di circa %.15f < accuracy\n", difference);
        }
        else if (Thread.currentThread().isInterrupted()) {
            System.out.printf("\nTerminazione programma per scadenza di tempo stabilito di %d secondi\n", timeout);
            System.out.printf("Con grado di accuratezza raggiunta di circa %.15f > accuracy\n", difference);
        }

        System.out.println("\nValore di Pi calcolato dal Thread è: " + piValue);
        System.out.println("Il valore di Math.PI è: " + Math.PI);

    }
}

