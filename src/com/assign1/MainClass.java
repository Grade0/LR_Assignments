package com.assign1;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.lang.Thread;

/**
 * @author Davide Chen
 * @version 1.0
 */

public class MainClass {

    public static void main(String[] args) {
        double accuracy = 0.0;
        int timeout = 0;
        //flag di uscita dal while, dopo aver letto opportunatamente i valori da input
        boolean readValue = false;

        //lettura da input di accuracy, si cicla finché non viene letto il valore corretto
        System.out.println("\n>>> PROGRAMMA PER IL CALCOLO DEL π MEDIANTE LA SERIE DI Gregory-Leibniz <<<\n");
        Scanner input = new Scanner(System.in);
        while (!readValue) {
            try {
                System.out.println("Inserire il grado di accuratezza (valore consentito: 0 < x < 1, esempio: 0,000005): ");
                accuracy = input.nextDouble();
                if(accuracy <= 0 || accuracy >= 1) {
                    System.out.println("\n!! Valore fuori dal range consentito, si prega di riprovare! !!");
                    continue;
                }
                readValue = true; //valore letto corretamente e si esce dal ciclo
            } catch (InputMismatchException e) {
                System.out.println("!! Assicurarsi di aver usato la virgola (,), invece del punto (.) per il numero in virgola mobile !!");
                System.out.println("L'input dev'essere di tipo double, si prega di riprovare!\n");
                input.next();
            }
        }

        readValue = false;

        //lettura da input di timeout, si cicla finché non viene letto il valore corretto
        while (!readValue) {
            try {
                System.out.println("Inserire durata esecuzione programma in secondi: ");
                timeout = input.nextInt();
                readValue = true; //valore letto correttamente e si esce dal ciclo
            } catch (InputMismatchException e) {
                System.out.println("L'input dev'essere di tipo int, si prega di riprovare!");
                input.next();
            }
        }
        input.close();

        //si crea la task
        PiGreco th1 = new PiGreco(accuracy, timeout);
        //si inserisce la task nel nuovo Thread creato
        Thread p = new Thread(th1);
        //si avvia l'esecuzione del Thread creato
        p.start();

        //dormo per "timeout" secondi prima di interrompere il Thread
        try {
            Thread.sleep(timeout*1000);
            p.interrupt();
        } catch (InterruptedException e) {
            System.out.println("Errore nell'interruzione del Thread");
        }
    }
}
