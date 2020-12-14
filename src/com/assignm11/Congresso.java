package com.assignm11;

import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;


public class Congresso extends RemoteServer implements InterfacciaCongresso {

    private final int days, sessions, slots;
    private final  GiornataCongresso[] giornate;

    Congresso(int nDays, int nSessionsPerDay, int maxSlotsPerSession) throws RemoteException {
        this.days = nDays;
        this.sessions = nSessionsPerDay;
        this.slots = maxSlotsPerSession;
        this.giornate = new GiornataCongresso[nDays];

        for (int i = 0; i < nDays; i++) this.giornate[i] = new GiornataCongresso(i+1, nSessionsPerDay, maxSlotsPerSession);
    }

    @Override
    public synchronized StringBuilder getSchedule() throws RemoteException {
        StringBuilder message = new StringBuilder();

        for (int giornata = 0; giornata < this.days; giornata++) {
            message.append("Giornata " + giornate[giornata].getnGiornata() + "\n");
            SessioneCongresso[] sessioni = this.getSessioni(giornata);

            for (int sessione = 0; sessione < this.sessions; sessione++) {
                message.append("S" + sessioni[sessione].getnSessione() + "\t");
                InterventoCongresso[] interventi = this.getInterventi(giornata, sessione);

                for (int slot = 0; slot < this.slots; slot++) {
                    message.append(slot+1 + ". ");
                    try {
                        message.append(interventi[slot].getNomeSpeaker() + ", ");
                    }
                    catch (NullPointerException e) {
                        message.append("____, ");
                    }
                }
                message.append("\n");
            }
            message.append("\n");
        }
        message.append("\n");

        return message;
    }

    @Override
    public synchronized boolean registerNewSpeaker(int nGiorno, int nSessione, int nSlot, String speaker) throws RemoteException {
        boolean esito;
        InterventoCongresso[] interventi = getInterventi(nGiorno, nSessione);
        if (interventi[nSlot] != null) {
            System.out.println("[UPDATE] Richiesta di registrazione per " + speaker + " rifiutata: slot occupato.");
            esito = false;
        }
        else {
            interventi[nSlot] = new InterventoCongresso(speaker);
            System.out.println("[UPDATE] Speaker " + speaker + " registrato correttamente nello slot (giorno/sessione/slot): " + (nGiorno+1) + "/" + (nSessione+1) + "/" + (nSlot+1));
            esito =  true;
        }

        return esito;
    }

    public synchronized GiornataCongresso[] getGiornate() throws RemoteException {
        return this.giornate;
    }

    public synchronized SessioneCongresso[] getSessioni(int nGiorno) throws RemoteException {
        return this.giornate[nGiorno].getSessioni();
    }

    public synchronized InterventoCongresso[] getInterventi(int nGiorno, int nSessione) throws RemoteException {
        SessioneCongresso[] sessioni = this.giornate[nGiorno].getSessioni();
        InterventoCongresso[] interventi = sessioni[nSessione].getInterventi();
        return interventi;
    }

    public synchronized String getSpeaker(int nGiorno, int nSessione, int nSlot) throws RemoteException {
        InterventoCongresso[] interventi = getInterventi(nGiorno, nSessione);
        return interventi[nSlot].getNomeSpeaker();
    }

    public synchronized boolean slotBooked(int nGiorno, int nSessione, int nSlot) throws RemoteException {
        InterventoCongresso[] interventi = getInterventi(nGiorno, nSessione);
        return interventi[nSlot] != null;
    }

    public synchronized boolean isInitialized() throws RemoteException {
        return this.giornate != null;
    }
}
