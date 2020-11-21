package com.assign4;

public class TutorMonitor {
    private final int NCOMPUTER = 20;
    private boolean professorePresente = false;
    private int utentiInLaboratorio = 0;
    private int professoriSospesi = 0;
    private int tesistiSospesi[] = new int[NCOMPUTER];
    private boolean computer[] = new boolean[NCOMPUTER];

    public TutorMonitor() {
        for(int i = 0; i < NCOMPUTER; i++) {
            this.computer[i] = false;
            this.tesistiSospesi[i] = 0;
        }
    }

    public synchronized int richiestaStudente(String username) {
        while(true) {
            while(professorePresente || professoriSospesi > 0 || utentiInLaboratorio == NCOMPUTER) {
                try {
                    System.out.println(" ----- " + username + ": professore in lab, in coda o laboratorio pieno, torno dopo... -----");
                    wait();
                    System.out.println(" ----- " + username + ": Signal ricevuto, ritento l'accesso... -----");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for(int indexPc = 0; indexPc < NCOMPUTER; indexPc++){
                if(!this.computer[indexPc] && this.tesistiSospesi[indexPc] == 0){
                    this.computer[indexPc] = true;
                    this.utentiInLaboratorio++;
                    return indexPc;
                }
            }
            try {
                System.out.println(" ----- " + username + ": tesista in coda, torno dopo -----");
                wait();
                System.out.println(" ----- " + username + ": Signal ricevuto, ritento l'accesso -----");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void fineRichiestaStudente(int indexPc) {
        this.computer[indexPc] = false;
        this.utentiInLaboratorio--;
        notifyAll();
    }

    public synchronized void inizioRichiestaTesista(int indexPc, String username) {
        while(professorePresente || professoriSospesi > 0 || computer[indexPc]) {
            try {
                tesistiSospesi[indexPc]++;
                System.out.println(" ----- " + username + ": professore presente, in coda o PC " + indexPc + " occupato, torno dopo... -----");
                wait();
                System.out.println(" ----- " + username + ": Signal ricevuto, ritento l'accesso... -----");
                tesistiSospesi[indexPc]--;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        computer[indexPc] = true;
        utentiInLaboratorio++;
    }

    public synchronized void fineRichiestaTesista(int indexPc) {
        this.computer[indexPc] = false;
        this.utentiInLaboratorio--;
        notifyAll();
    }

    public synchronized void inizioRichiestaProfessore(String username) {
        while(utentiInLaboratorio > 0)
            try {
                professoriSospesi++;
                System.out.println(" ----- " + username + ": ci sono Utenti attivi nel laboratorio, attendo che finiscono... -----");
                wait();
                professoriSospesi--;
                System.out.println(" ----- " + username + ": Signal ricevuto, ritento l'accesso... -----");
            } catch (InterruptedException e ) {
                e.printStackTrace();
            }
        for(int i = 0; i < NCOMPUTER; i++) {
            computer[i] = true;
        }
        utentiInLaboratorio = 20;
        professorePresente = true;
    }

    public synchronized void fineRichiestaProfessore() {
        for(int i = 0; i < NCOMPUTER; i++)
            computer[i] = false;
        utentiInLaboratorio = 0;
        this.professorePresente = false;
        notifyAll();
    }
}
