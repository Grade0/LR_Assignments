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
    String getSchedule() throws RemoteException;

    /**
     * @param nGiorno il giorno scelto
     * @param nSessione la sessione scelta
     * @param nIntervento lo slot scelto
     * @return      true se l'intervento è stato correttamente registrato
     *              false se lo slot è già stato occupato
     * @throws RemoteException se si verificano durante l'esecuzione della chiamata remota
     */
    boolean registerNewSpeaker(int nGiorno, int nSessione, int nIntervento, String speaker) throws RemoteException;

    /**
     * @return L'insieme delle giornate del congresso
     * @throws RemoteException se si verificano durante l'esecuzione della chiamata remota
     */
    GiornataCongresso[] getGiornate() throws RemoteException;

    /**
     * @param nGiorno il giorno a cui sono riferiti le sessioni
     * @return L'insieme delle sessioni di una giorvata
     * @throws RemoteException se si verificano durante l'esecuzione della chiamata remota
     */
    SessioneCongresso[] getSessioni(int nGiorno) throws RemoteException;

    /**
     * @param nGiorno il giorno a cui è riferito la sessione
     * @param nSessione la sessione a cui è riferitaìo l'intervento
     * @return L'insieme degli interventi di una sessione di un certo giorno
     * @throws RemoteException se si verificano durante l'esecuzione della chiamata remota
     */
    InterventoCongresso[] getInterventi(int nGiorno, int nSessione) throws RemoteException;
}
