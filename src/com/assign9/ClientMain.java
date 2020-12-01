package com.assign9;

public class ClientMain {
    /**
     * Controlla i parametri e avvia il client
     * @param args Parametri del programma
     */
    public static void main(String[] args) {
        if (args.length != 2) System.err.println("Usage: java PingClient hostname port");
        else {
            try {
                int port = Integer.parseInt(args[1]);
                new Thread(new PingClient(args[0], port)).start();
            } catch (NumberFormatException ex) { System.err.println("ERR - arg 2"); }
        }
    }
}
