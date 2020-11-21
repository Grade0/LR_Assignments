package com.assign3;

/**
 * @author Davide Chen
 * @version 1.0
 */

public abstract class Utente implements Runnable {
    //Classe astratta Utente

    //metodo richiesta utilizzo pc al tutor
    public abstract void richiediComputer(int i);
    //metodo per simulare l'utilizzo del computer
    public abstract void useComputer(int i);
    //metodo run della Task
    public abstract void run();

}
