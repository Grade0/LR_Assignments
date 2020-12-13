package com.assignm11;

import java.io.Serializable;

/**
 * Singola sessione di congresso nella giornata, contenente i 5 interventi
 */
public class SessioneCongresso implements Serializable {
    /**
     * Il numero della sessione di congresso
     */
    private int nSessione;

    /**
     * I 5 interventi della sessione di congresso
     */
    private InterventoCongresso[] interventi;

    /**
     * Costruttore della classe che istanzia gli attributi
     * @param n il numero della sessione all'interno della giornata di congresso
     */
    public SessioneCongresso(int n, int maxParticipations) {
        this.nSessione = n;
        this.interventi = new InterventoCongresso[maxParticipations];
    }

    public InterventoCongresso[] getInterventi() {
        return interventi;
    }

    public int getnSessione() {
        return nSessione;
    }
}
