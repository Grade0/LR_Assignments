package com.assign9;

public class ServerMain {
    /**
     * Controlla i parametri da linea di comando e in caso lancia il server
     * @param args Parametri del programma
     */
    public static void main(String[] args) {
        if (args.length != 1) System.err.println("Usage: java PingServer port");
        else {
            try {
                int port = Integer.parseInt(args[0]);
                new Thread(new PingServer(port)).start();
            }
            catch (NumberFormatException ex) { System.err.println("ERR - arg 1"); }

        }
    }
}
