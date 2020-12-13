package com.assignm11;

import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;


public class Congresso extends RemoteServer implements InterfacciaCongresso {

    private GiornataCongresso[] giornate;

    Congresso(int nDays, int nSessionsPerDay, int maxParticipationsPerSession) throws RemoteException {
        this.giornate = new GiornataCongresso[nDays];
        for (int i = 0; i < nDays; i++) this.giornate[i] = new GiornataCongresso(i+1, nSessionsPerDay, maxParticipationsPerSession);
    }

    @Override
    public String getSchedule() throws RemoteException {
        String message = "";
        for (int i = 0; i < 3; i++) {
            message += "Giornata " + giornate[i].getnGiornata() + "\n";
            SessioneCongresso[] sessioni = this.getSessioni(i);
            for (int j = 0; j < 12; j++) {
                message += "S" + sessioni[j].getnSessione() + "\t";
                InterventoCongresso[] interventi = this.getInterventi(i, j);
                for (int k = 0; k < 5; k++) {
                    message += k+1 + ". ";
                    try {
                        message += interventi[k].getNomeSpeaker() + ", ";
                    }
                    catch (NullPointerException e) {
                        message +=  "____, ";
                    }
                }
                message += "\n";
            }
            message += "\n";
        }
        message += "\n";

        return message;
    }

    @Override
    public boolean registerNewSpeaker(int nGiorno, int nSessione, int nIntervento, String speaker) throws RemoteException {
        boolean esito;
        InterventoCongresso[] interventi = getInterventi(nGiorno, nSessione);
        if (interventi[nIntervento] != null) {
            System.out.println("[UPDATE] Richiesta di registrazione per " + speaker + " rifiutata: slot occupato.");
            esito = false;
        }
        else {
            interventi[nIntervento] = new InterventoCongresso(speaker);
            System.out.println("[UPDATE] Speaker " + speaker + " registrato correttamente nello slot (giorno/sessione/slot): " + (nGiorno+1) + "/" + (nSessione+1) + "/" + (nIntervento+1));
            esito =  true;
        }

        return esito;
    }

    public GiornataCongresso[] getGiornate() {
        return this.giornate;
    }

    public SessioneCongresso[] getSessioni(int nGiorno) {
        return this.giornate[nGiorno].getSessioni();
    }

    public InterventoCongresso[] getInterventi(int nGiorno, int nSessione) {
        SessioneCongresso[] sessioni = this.giornate[nGiorno].getSessioni();
        InterventoCongresso[] interventi = sessioni[nSessione].getInterventi();
        return interventi;
    }
}
