package com.assignm11;

/**
 * Singola sessione di congresso nella giornata, contenente i 5 interventi
 */
public class SessioneCongresso {
    //Il numero della sessione di congresso
    private final int nSessione;

    //I 5 interventi della sessione di congresso
    private final InterventoCongresso[] interventi;

    /**
     * Costruttore della classe che istanzia gli attributi
     * @param n il numero della sessione all'interno della giornata di congresso
     * @param maxSlots il numero massimo di interventi
     */
    public SessioneCongresso(int n, int maxSlots) {
        this.nSessione = n;
        this.interventi = new InterventoCongresso[maxSlots];
    }

    public InterventoCongresso[] getInterventi() {
        return interventi;
    }

    public int getnSessione() {
        return nSessione;
    }
}
