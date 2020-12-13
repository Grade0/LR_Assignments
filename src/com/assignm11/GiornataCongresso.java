package com.assignm11;

import java.io.Serializable;

/**
 * Singola giornata di congresso, contenente le 12 sessioni
 */
public class GiornataCongresso implements Serializable {

    //Il numero di giornata del congresso
    private int nGiornata;

    //Le 12 sessioni della giornata di congresso
    private SessioneCongresso[] sessioni;

    /**
     * Costruttore della classe che istanzia gli attributi
     * @param n la giornata di congresso
     * @param nSession numero di sessione per giorno
     * @param maxSpeakerPerSession numero massimo di interventi per sessione
     */
    public GiornataCongresso(int n, int nSession, int maxSpeakerPerSession) {
        this.nGiornata = n;
        this.sessioni = new SessioneCongresso[nSession];
        for (int i = 0; i < 12; i++) {
            this.sessioni[i] = new SessioneCongresso(i+1, maxSpeakerPerSession);
        }
    }

    public SessioneCongresso[] getSessioni() {
        return sessioni;
    }

    public int getnGiornata() {
        return nGiornata;
    }
}
