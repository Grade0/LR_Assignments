package com.assignm11;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class MainServer {
    final static int nGIORNATE = 3;
    final static int nSESSIONI_PER_GIORNATA = 12;
    final static int nINTERVENTI_PER_SESSIONE = 5;

    public static void main(String[] args) {


        if (args.length != 1) System.err.println("Usage: server <porta>");
        else {
            try {
                int port = Integer.parseInt(args[0]);
                if (port < 1024) throw new NumberFormatException();
                Congresso congresso = new Congresso(nGIORNATE, nSESSIONI_PER_GIORNATA, nINTERVENTI_PER_SESSIONE);
                //Esportazione dell'oggetto
                InterfacciaCongresso stub = (InterfacciaCongresso) UnicastRemoteObject.exportObject(congresso, port);

                //Crezione del registro
                LocateRegistry.createRegistry(port);
                Registry r = LocateRegistry.getRegistry(port);

                //Binding
                r.rebind("CONGRESSO-SERVER", stub);

                //Server pronto
                System.out.println("------ Congresso istanziato ------");
                System.out.println("Giorni: " + nGIORNATE + "\nSessioni per giorno: " + nSESSIONI_PER_GIORNATA + "\nSlot interventi per sessione: " + nINTERVENTI_PER_SESSIONE);
                System.out.println("----------------------------------\n");
                System.out.println("Server pronto su porta " + port);
            }
            catch (NumberFormatException e) {
                System.err.println("Il parametro inserito non è una porta valida");
            }
            catch (RemoteException e) {
                System.err.println("Errore di comunicazione: " + e.getMessage());
            }
        }
    }
}
