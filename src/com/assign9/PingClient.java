package com.assign9;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;


public class PingClient implements Runnable {
    private InetAddress hostname;
    private int port;

    /**
     * Costruisce il client su porta e host indicati
     * @param hostname l'host a cui connettersi
     * @param port la porta alla quale connettersi
     */
    public PingClient(String hostname, int port) {
        try { this.hostname = InetAddress.getByName(hostname); }
        catch (UnknownHostException e) { System.err.println("ERR - arg 1");}
        this.port = port;
    }

    @Override
    public void run() {
        int transmitted, received = 0;
        long RTTmin = 1000, RTTmax = 0;
        float RTTavg, RTTsum = 0;

        System.out.println("Launching " + this.getClass().getSimpleName() + " on " + this.hostname + ":" + this.port);

        try(DatagramSocket clientSocket = new DatagramSocket()) {

            //Creo il socket con timeout di 2s
            clientSocket.setSoTimeout(2000);
            for (transmitted = 0; transmitted < 10; transmitted++) {
                try {
                    //n-simo PING <timestamp>
                    byte[] buffer = ("PING " + transmitted + " " + System.currentTimeMillis()).getBytes(StandardCharsets.UTF_8);
                    DatagramPacket packetToSend = new DatagramPacket(buffer, buffer.length, this.hostname, this.port);
                    System.out.print(new String(buffer, StandardCharsets.UTF_8) + " RTT: ");
                    clientSocket.send(packetToSend);

                    //Tempo attuale, per misurare l'RTT in ms
                    long time = System.currentTimeMillis();
                    try {
                        //Apetto il pacchetto
                        clientSocket.receive(packetToSend);

                        //Se lo ricevo, registro le statistiche: RTT, ricevuti ecc.
                        time = System.currentTimeMillis() - time;
                        System.out.println(time + " ms");
                        if (RTTmin > time) RTTmin = time;
                        if (RTTmax < time) RTTmax = time;
                        RTTsum += time;

                        received++;
                    } catch (SocketTimeoutException ex) {
                        //Timeout, datagramma perso
                        System.out.println("*");
                    }
                }
                catch (BindException e) {
                    System.err.println("Couldn't open server on port " + this.port);
                }
                catch (IOException e) {
                    System.err.println("IOException error on packet " + transmitted + ". Resending");
                    transmitted--;
                }

            }

            RTTavg = RTTsum / received;
            double loss = ((transmitted - received)/(transmitted * 1.0)) * 100;

            //Stampa delle statistiche richieste
            System.out.println("\t\t\t\t---- PING Statistics ----");
            System.out.printf(transmitted + " packets transmitted, " + received + " packets received, %1.0f%% packet loss\n", loss);
            System.out.printf("RTT (ms) min/avg/max = " + RTTmin + "/%1.2f/" + RTTmax + "\n", RTTavg);
        }

        catch (IOException e) {
            System.err.println("IOException on receive: " + e.getMessage());
        }
    }
}