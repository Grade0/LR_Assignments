package com.assign9;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.Random;


public class PingServer implements Runnable{
    private final int port;

    /**
     * Costruisce il server sulla porta indicata
     * @param port La porta su cui ascoltare i datagrammi UDP
     */
    public PingServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        System.out.println(this.getClass().getSimpleName() + " ONLINE, listening on port " + this.port);
        try {
            DatagramSocket clientSocket = new DatagramSocket(this.port);
            clientSocket.setSoTimeout(60000);
            int BUFFER_SIZE = 128;

            try {
                do {
                    byte[] buff = new byte[BUFFER_SIZE];
                    DatagramPacket packet = new DatagramPacket(buff, buff.length);
                    clientSocket.receive(packet);
                    System.out.print(packet.getAddress().getHostAddress() + ":" + packet.getPort() + "> " + new String(packet.getData(), StandardCharsets.UTF_8).trim());

                    //Decido cosa fare
                    boolean losePacket = getAction();
                    if (!losePacket) {
                        int delay = getDelay();
                        System.out.println(" ACTION: delayed " + delay + " ms");

                        try {
                            Thread.sleep(delay);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        clientSocket.send(packet);
                    } else {
                        System.out.println(" ACTION: not sent");
                    }
                } while (true);
            }
            catch (SocketTimeoutException ex) {
                //Closing the server on timeout
                System.err.println("Timeout server, 1m");
            }
            catch (IOException e) {
                System.err.println("IOException on receive: " + e.getMessage());
            }
        }
        catch (SocketException ex) {
            System.err.println("Couldn't open server on port " + this.port);
        }
    }

    /**
     * Decide l'azione da intraprendere: ritrasmettere il pacchetto o perderlo con probabilità del 25%.
     *
     * @return {@code false} se decide di ritrasmettere il pacchetto,
     *         {@code true} se decide di perderlo
     */
    public boolean getAction() {
        Random rnd = new Random(System.currentTimeMillis());
        int n = rnd.nextInt(100);
        return n < 25;
    }

    /**
     * Ritorna un numero casuale compreso tra 50 e 499
     * @return il numero casuale
     */
    public int getDelay() {
        Random rnd = new Random(System.currentTimeMillis());
        return rnd.nextInt(450) + 50;
    }
}