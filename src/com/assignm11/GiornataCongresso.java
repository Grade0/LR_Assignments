package com.assignm11;

/**
 * Singola giornata di congresso, contenente le 12 sessioni
 */
public class GiornataCongresso{

    //Il numero di giornata del congresso
    private final int nGiornata;

    //Le 12 sessioni della giornata di congresso
    private final SessioneCongresso[] sessioni;

    /**
     * Costruttore della classe che istanzia gli attributi
     * @param n la giornata di congresso
     * @param nSession numero di sessione per giorno
     * @param maxSlotsPerSession numero massimo di interventi per sessione
     */
    public GiornataCongresso(int n, int nSession, int maxSlotsPerSession) {
        this.nGiornata = n;
        this.sessioni = new SessioneCongresso[nSession];
        for (int i = 0; i < nSession; i++) {
            this.sessioni[i] = new SessioneCongresso(i+1, maxSlotsPerSession);
        }
    }

    public SessioneCongresso[] getSessioni() {
        return sessioni;
    }

    public int getnGiornata() {
        return nGiornata;
    }
}
