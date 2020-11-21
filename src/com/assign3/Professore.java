package com.assign3;

/**
 * @author Davide Chen
 * @version 1.0
 */

public class Professore extends Utente {
    //username nel formato [Professore n.1]
    final String username;
    //valore rappresentante il numero di accessi che l'utente deve fare
    final int k;
    //il nostro tutor
    final Tutor tutor;

    public Professore(int n, Tutor t, String ruolo, int i) {
        username = ruolo + " n." + i;
        k = n;
        tutor = t;
    }

    //metodo richiesta utilizzo pc al tutor
    @Override
    public void richiediComputer(int i) {
        tutor.inizioRichiestaProfessore(username);
        useComputer(i);
        tutor.fineRichiestaProfessore();
    }

    //metodo per simulare l'utilizzo del computer
    @Override
    public void useComputer(int i) {
        System.out.println(username + ": LOGIN effettuato su tutti i computer del laboratorio per la " + i + "^ volta");

        try {
            Thread.sleep((int) (Math.random()*4000) + 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(username + ": LOGOUT effettuato sul tutti i computer del laboratorio");
    }

    public void run() {
        System.out.println(username + ": INIZIO THREAD - numero accessi k = " + k);
        try {
            for (int i = 0; i < k; i++) {
                richiediComputer(i+1);
                //attesa prima di inviare nuova richiesta
                Thread.sleep((int) (Math.random()*4000) + 1000);
            }
            System.out.println(username + ": FINE THREAD");
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

}
