package com.assign2;

public class Cliente implements Runnable {
    //numerino della fila
    int numerinoFila;

    public Cliente(int n) {
        this.numerinoFila = n;
    }

    public void run() {
        //inizio servzio allo sportello del cliente
        System.out.printf("Sportello \"%s\" SERVE ORA il cliente n. %d\n", Thread.currentThread().getName(), numerinoFila);

        try {
            //attendo per un tempo t con 10ms < t < 60ms
            //ovvero tempo impiegato per il servizio richiesto
            Thread.sleep((int) (Math.random() * 50) + 10);
        } catch (InterruptedException e) {
            System.out.println("Servizio al cliente n. " + numerinoFila + "interrotto");
        }

        //fine servizio allo sportello del cliente
        System.out.printf("Sportello \"%s\" HA FINITO di servire il cliente n. %d\n", Thread.currentThread().getName(), numerinoFila);
    }
}

