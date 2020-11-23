package com.assign5;

import java.io.*;

public class MainClass {

    public static void main(String[] args){
        //numero di Thread consumatori generati a random tra 1 e 4
        int nConsumer = (int) (Math.random() * 4) + 1;
        //si passa da linea di comando il filepath della directory
        File inputFile = new File(args[0]);

        //verifico che il filepath passato sia effettivamente
        //altrimenti termino subito
        if(!inputFile.isDirectory()) {
            System.out.println("Il file iniziale non è una directory");
            System.exit(-1);
        }

        //Stringa speciale di terminazione condivisa fra i Thread
        //serve al Produttore per comunicare ai Consumatori
        //che ha finito di visitare tutte le directory e sottodirectory
        String endOfSearchString = "EOS";

        //Creo il la mia coda testBuffer
        FilePathBuffer testBuffer = new FilePathBuffer();
        //Creo ed avvio il Thread Produttore
        Thread producerThread = new Thread(new Producer(inputFile.getPath(), testBuffer, nConsumer, endOfSearchString));
        producerThread.start();

        //creo ed avvio i miei Consumatori
        for(int i = 0; i < nConsumer; i++){
            Thread newReader = new Thread(new Consumer(testBuffer, endOfSearchString));
            newReader.start();
        }

    }
}
