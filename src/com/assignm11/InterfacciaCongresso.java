package com.assignm11;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfaccia di accesso al congresso
 */
public interface InterfacciaCongresso extends Remote {

    /**
     *
     * @return il calendario del congresso
     * @throws RemoteException se si verificano durante l'esecuzione della chiamata remota
     */
    StringBuilder getSchedule() throws RemoteException;

    /**
     * @param nGiorno il giorno scelto
     * @param nSessione la sessione scelta
     * @param nSlot lo slot scelto
     * @return      true se l'intervento è stato correttamente registrato
     *              false se lo slot è già stato occupato
     * @throws RemoteException se si verificano durante l'esecuzione della chiamata remota
     */
    boolean registerNewSpeaker(int nGiorno, int nSessione, int nSlot, String speaker) throws RemoteException;

    /**
     * @return L'insieme delle giornate del congresso
     * @throws RemoteException se si verificano durante l'esecuzione della chiamata remota
     */
    GiornataCongresso[] getGiornate() throws RemoteException;

    /**
     * @param nGiorno il giorno a cui sono riferite le sessioni
     * @return L'insieme delle sessioni di una giorvata
     * @throws RemoteException se si verificano durante l'esecuzione della chiamata remota
     */
    SessioneCongresso[] getSessioni(int nGiorno) throws RemoteException;

    /**
     * @param nGiorno il giorno degli intevernti
     * @param nSessione la sesssione degli interventi
     * @return L'insieme degli interventi di una sessione di un certo giorno
     * @throws RemoteException se si verificano durante l'esecuzione della chiamata remota
     */
    InterventoCongresso[] getInterventi(int nGiorno, int nSessione) throws RemoteException;

    /**
     * @param nGiorno il giorno dell'intervento dello speaker
     * @param nSessione la sessione dell'intervento dello spekaer
     * @param nSlot lo slot dell'intervento dello speaker
     * @return Il nome dello skeaker di un certo giorno, sessione e slot
     * @throws RemoteException se si verificano durante l'esecuzione della chiamata remota
     */
    String getSpeaker(int nGiorno, int nSessione, int nSlot) throws RemoteException;

    /**
     * @param nGiorno il giorno dell'intervento dello speaker
     * @param nSessione la sessione dell'intervento dello spekaer
     * @param nSlot lo slot dell'intervento dello speaker
     * @return      true se lo slot è già stato prenotato
     *              false se lo slot è libero
     * @throws RemoteException se si verificano durante l'esecuzione della chiamata remota
     */
    boolean slotBooked(int nGiorno, int nSessione, int nSlot) throws RemoteException;

    /**
     *
     * @return      true se il congresso è stato già inizializzato
     *              false altimenti
     * @throws RemoteException se si verificano durante l'esecuzione della chiamata remota
     */
    boolean isInitialized() throws RemoteException;
}
