package com.assign3;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Davide Chen
 * @version 1.0
 */

public class LaboratorioMain {
    //costante numero di PC del laboratorio
    final static int NCOMPUTER = 20;

    public static void main(String[] args) {
        //flag per verifica avvenuta lettura da riga di comando
        boolean readValue = false;

        //numero di professori, tesisti e studenti prima della lettura in input
        int nProfessori = 0;
        int nTesisti = 0;
        int nStudenti = 0;

        //il nostro tutor
        Tutor t = new Tutor(NCOMPUTER);

        //Threadpool
        ExecutorService threadsUtenti = Executors.newCachedThreadPool();

        //cicli di lettura da riga di comando di nProfessori, nTesisti e nStudenti
        //il ciclo va avanti finché non legge un valore opportuno
        Scanner input = new Scanner(System.in);
        while (!readValue) {
            try {
                System.out.println("Inserire il numero di Professori:");
                nProfessori = input.nextInt();
                if(nProfessori < 0) {
                    System.out.println("\n!! Valore non può essere negativo, si prega di riprovare! !!");
                    continue;
                }
                readValue = true; //valore letto corretamente e si esce dal ciclo
            } catch (InputMismatchException e) {
                System.out.println("L'input dev'essere di tipo int, si prega di riprovare!\n");
                input.next();
            }
        }

        readValue = false;
        while (!readValue) {
            try {
                System.out.println("Inserire il numero di Tesisti:");
                nTesisti = input.nextInt();
                if(nTesisti < 0) {
                    System.out.println("\n!! Valore non può essere negativo, si prega di riprovare! !!");
                    continue;
                }
                readValue = true; //valore letto corretamente e si esce dal ciclo
            } catch (InputMismatchException e) {
                System.out.println("L'input dev'essere di tipo int, si prega di riprovare!\n");
                input.next();
            }
        }

        readValue = false;
        while (!readValue) {
            try {
                System.out.println("Inserire il numero di Studenti:");
                nStudenti = input.nextInt();
                if(nStudenti < 0) {
                    System.out.println("\n!! Valore non può essere negativo, si prega di riprovare! !!");
                    continue;
                }
                readValue = true; //valore letto corretamente e si esce dal ciclo
            } catch (InputMismatchException e) {
                System.out.println("L'input dev'essere di tipo int, si prega di riprovare!\n");
                input.next();
            }
        }
        input.close();


        //creazione dei professori e inserimento nella Threadpool
        for(int i = 1; i <= nProfessori; i++) {
            //generatore per numero random di volte che ogni utente lavora (fissato da me tra 1 e 4)
            int k = (int) (Math.random() * 4) + 1;
            threadsUtenti.execute(new Professore(k, t, "Professore", i));
        }

        //creazione dei tesisti e inserimento nella Threadpool
        for(int i = 1; i <= nTesisti; i++) {
            //generatore per numero random di volte che ogni utente lavora (fissato da me tra 1 e 4)
            int k = (int) (Math.random() * 4) + 1;

            //indice del computer da assegnare ai tesisti, generato casualmente da 1 a NCOMPUTER
            int indexPc = (int) (Math.random() * NCOMPUTER) + 1;
            threadsUtenti.execute(new Tesista(k, t, indexPc, "Tesista", i));
        }

        //creazione dei studenti e inserimento nella Threadpool
        for(int i = 1; i <= nStudenti; i++) {
            //generatore per numero random di volte che ogni utente lavora (fissato da me tra 1 e 4)
            int k = (int) (Math.random() * 4) + 1;
            threadsUtenti.execute(new Studente(k, t, "Studente", i));
        }

        //shutdown della threadpool
        threadsUtenti.shutdown();

        //attendo finché tutti i thread siano terminati
        while(!threadsUtenti.isTerminated()){}

        System.out.println("\nTutti gli Utenti hanno finito - Laboratorio chiuso");

    }
}

