package com.assignm11;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class MainClient {

    public static void main(String[] args) {
        if (args.length != 1) System.err.println("Usage: client <porta>");
        else {
            try {
                int port = Integer.parseInt(args[0]);
                if (port < 1024) throw new NumberFormatException();

                // ottiene una reference per il registro
                Registry r = LocateRegistry.getRegistry(port);
                // ottiene una reference all'event manager
                InterfacciaCongresso congresso = (InterfacciaCongresso) r.lookup("CONGRESSO-SERVER");

                if(congresso != null) {
                    System.out.println("Client connesso al server su porta " + port);
                    avvioMenu(congresso);
                }
                else {
                    System.err.println("Errore nel caricamento del congresso.");
                }
            }
            catch (NumberFormatException e) {
                System.err.println("Il parametro inserito non è una porta valida");
            }
            catch (Exception ex) {
                System.err.println("Errore"); ex.printStackTrace();
            }
        }
    }


    private static void printMenu() {
        System.out.println("****\tMenù di accesso alla piattaforma");
        System.out.println("****\t\t1. Registrare uno speaker per una sessione");
        System.out.println("****\t\t2. Visualizzare il programma del congresso");
        System.out.println("****\t\t0. Uscire");
        System.out.print("****\t\t> ");
    }

    public static void avvioMenu(InterfacciaCongresso congresso) {

        int n;
        Scanner input = new Scanner(System.in);

        do {
            printMenu();
            n = input.nextInt();

            switch (n) {
                case 1:
                    System.out.println("Raccolta informazioni dal server");
                    try {
                        int giorno, sessione, intervento;
                        String name;

                        do {
                            System.out.print("A quale giornata ci si vuole registrare? 0 per annullare\n[1-3] ");
                            giorno = input.nextInt();
                        } while (giorno < 0 || giorno > 3);
                        if (giorno != 0) {
                            //congresso.getGiornate()[giorno - 1];
                            do {
                                System.out.print("A quale sessione ci si vuole iscrivere?\n[1-12] ");
                                sessione = input.nextInt();
                            } while(sessione < 1 || sessione > 12);

                            //congresso.getGiornate()[giorno - 1].sessioni[sessione - 1];
                            do {
                                System.out.print("A quale intervento ci si vuole iscrivere?\n[1-5] ");
                                intervento = input.nextInt();
                            } while (intervento < 1 || intervento > 5);

                            if (congresso.getInterventi(giorno - 1, sessione - 1)[intervento - 1] == null) {
                                System.out.print("Perfetto, a che nome? ");
                                name = input.next();
                                if (congresso.registerNewSpeaker(giorno - 1, sessione - 1, intervento - 1, name)) {
                                    System.out.println("Speaker " + congresso.getInterventi(giorno - 1, sessione - 1)[intervento - 1].getNomeSpeaker() + " registrato con successo!");
                                } else System.err.println("Errore");
                                //congresso.getGiornate()[giorno - 1].sessioni[sessione - 1].interventi[intervento - 1];
                            } else {
                                //congresso.getGiornate()[giorno - 1].sessioni[sessione - 1].interventi[intervento - 1];
                                System.err.println("Spiacenti, intervento già prenotato da " + congresso.getInterventi(giorno - 1, sessione - 1)[intervento - 1].getNomeSpeaker());
                            }
                        }
                    }
                    catch (RemoteException e) {
                        e.printStackTrace();
                    }

                    break;
                case 2:
                    System.out.println("Recupero del programma dal server");
                    try {
                        if (congresso.getGiornate() == null)
                            System.out.println("Il congresso è ancora vuoto.");
                        else {
                            System.out.println(congresso.getSchedule());
                        }
                    }
                    catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                case 0:
                    System.out.println("****\tGrazie per aver usato la piattaforma, arrivederci!");
                    break;
                default:
                    System.err.println("Comando non riconosciuto, riprovare.");
                    n = -1;
                    break;
            }
        } while (n != 0);
        input.close();
    }
}