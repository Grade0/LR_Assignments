package com.assign3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Davide Chen
 * @version 1.0
 */

public class Tutor {
    //numero di pc del laboratorio
    final int NCOMPUTER;
    //lock delle risorse (PC)
    final ReentrantLock l;
    //VR del professore
    final Condition professoreInAttesa;
    //VR dello studente
    final Condition studenteInAttesa;
    //VR del tesista
    final Condition[] iesimoOccupato;
    //array di flag dei pc in uso
    boolean[] computerOccupato;

    public Tutor(int n) {
        NCOMPUTER = n;
        l = new ReentrantLock();
        professoreInAttesa = l.newCondition();
        studenteInAttesa = l.newCondition();
        iesimoOccupato = new Condition[NCOMPUTER];
        for(int i = 0; i < NCOMPUTER; i++) {
            iesimoOccupato[i] = l.newCondition();
        }
        computerOccupato = new boolean[NCOMPUTER];
        for(int i = 0; i < NCOMPUTER; i++) {
            computerOccupato[i] = false;
        }
    }

    //restituisce l'indice del primo pc libero, altrimenti restituisce -1
    private int getComputerLibero() {
        for(int i =0; i < NCOMPUTER; i++) {
            //verifica che il pc non sia occupato e che non ci sia un tesista in attesa di usarlo
            if (!computerOccupato[i] && !l.hasWaiters(iesimoOccupato[i])) {
                return i;
            }
        }
        return -1;
    }

    //restituisce il numero di pc liberi nel laboratorio (usato dal prof)
    private int getNumComputerLiberi() {
        int res = 0;
        for(int i = 0; i < NCOMPUTER; i++) {
            if(!computerOccupato[i]) {
                res++;
            }
        }
        return res;
    }

    //richiesta dello studente all'utilizzo del pc
    public int inizioRichiestaStudente(String username) {
        l.lock();
        try {
            try {
                //aspetto se ho professori in coda o non ho computers disponibili
                while(l.hasWaiters(professoreInAttesa) || getComputerLibero() == -1) {
                    System.out.println(" ----- " + username + ": professore in coda o laboratorio pieno, torno dopo -----");
                    studenteInAttesa.await();
                    System.out.println(" ----- " + username + ": Signal ricevuto, ritento l'accesso -----");
                }
                int res = getComputerLibero();
                computerOccupato[res] = true;
                return res;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            l.unlock();
        }
        return -1; //segnale di errore
    }

    //termine richiesta utilizzo del pc, usato sia dal tesista che dallo studente
    public void fineRichiestaNoProfessore(int s) {
        l.lock();
        try {
            //rilascio il computer
            computerOccupato[s] = false;

            //se c'è un prof in attesa -> informo che è stato rilasciato il computer
            //se non ci sono prof allora se c'è tesista informo lui, altrimenti
            //informo gli studenti che un computer si è liberato
            if(l.hasWaiters(professoreInAttesa)) {
                professoreInAttesa.signal();
            }
            else if(l.hasWaiters(iesimoOccupato[s])) {
                iesimoOccupato[s].signal();
            }
            else {
                studenteInAttesa.signal();
            }
        } finally {
            l.unlock();
        }
    }

    //richiesta del tesista all'utilizzo del pc
    public void inizioRichiestaTesista(int idx, String username) {
        l.lock();
        try {
            try {
                //aspetto se ho professori in coda o se il computer è occupato
                while(l.hasWaiters(professoreInAttesa) || computerOccupato[idx]) {
                    System.out.println(" ----- " + username + ": professore in coda o PC " + idx + " occupato, torno dopo... -----");
                    iesimoOccupato[idx].await();
                    System.out.println(" ----- " + username + ": Signal ricevuto, ritento l'accesso... -----");
                }
                computerOccupato[idx] = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            l.unlock();
        }
    }

    //richiesta del professore all'uso del laboratorio
    public void inizioRichiestaProfessore(String username) {
        l.lock();
        try {
            try {
                //se ci sono PC occupati -> aspetto
                //nota che siccome professoreInAttesa ha "priorità maggiore" allora piano piano
                //i computer si liberano tutti prima di essere occupati di nuovo da studenti o tesisti
                while(getNumComputerLiberi() != NCOMPUTER) {
                    System.out.println(" ----- " + username + ": ci sono Utenti attivi nel laboratorio, attendo che finiscono... -----");
                    professoreInAttesa.await();
                    System.out.println(" ----- " + username + ": Signal ricevuto, ritento l'accesso... -----");
                }
                for(int i = 0; i < NCOMPUTER; i++) {
                    computerOccupato[i] = true;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            //tolgo lock
            l.unlock();
        }
    }

    //fine richiesta professore all'uso del laboratorio
    public void fineRichiestaProfessore() {
        l.lock();
        try {
            //rilascio tutti i computer
            for(int i = 0; i < NCOMPUTER; i++) {
                computerOccupato[i] = false;
            }
            //se ci sono prof in attesa -> informo che sono tutti liberi
            //se non ci sono prof -> informo tutti i tesisti che sono liberi e dopodiché informo
            //anche gli studenti. Nota che potrei avere più di un computer libero (dopo
            //che gli eventuali tesisti ne hanno occupati alcuni) quindi devo fare signalAll
            if(l.hasWaiters(professoreInAttesa)) {
                professoreInAttesa.signal();
            }
            else {
                for(int i = 0; i < NCOMPUTER; i++) {
                    iesimoOccupato[i].signal();
                }
                studenteInAttesa.signalAll();
            }
        } finally {
            l.unlock();
        }
    }
}

