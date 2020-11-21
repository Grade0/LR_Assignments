package com.assign4;

/**
 * @author Davide Chen
 * @version 1.0
 */

public class Studente extends Utente {
    //username nel formato [Studente n.1]
    final String username;
    //valore rappresentante il numero di accessi che l'utente deve fare
    final int k;
    //il nostro tutor
    final TutorMonitor tutor;
    //indice del computer che gli serve
    int indexPc;

    public Studente(int n, TutorMonitor t, String ruolo, int i) {
        username = ruolo + " n." + i;
        k = n;
        tutor = t;
    }

    //metodo richiesta utilizzo pc al tutor
    @Override
    public void richiediComputer(int i) {
        System.out.println(username + ": richiesta computer" + " per la " + i + "^ volta");
        indexPc = tutor.richiestaStudente(username);
        System.out.println(username + ": assegnato il pc n." + indexPc);
        useComputer();
        tutor.fineRichiestaStudente(indexPc);
    }

    //metodo per simulare l'utilizzo del computer
    @Override
    public void useComputer() {
        try {
            Thread.sleep((int) ((Math.random()*4000) + 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
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

