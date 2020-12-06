package com.assign10;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;


public class TimeClient {
    //indirizzo del gruppo di multicast
    private InetAddress dateGroup;
    //porta associata all'indirizzo multicast
    private int port;
    //lunghezza del messaggio da ricevere
    private final int BUFF_SIZE = 128;

    /**
     *
     * @param addr indirizzo del gruppo di multicast
     * @param port porta a cui associare il socket di multicast
     * @throws UnknownHostException se l'indirizzo non è valido
     * @throws IllegalArgumentException se l'indirizzo non è un indirizzo di multicast
     */
    public TimeClient(String addr, int port) throws UnknownHostException, IllegalArgumentException {
        this.dateGroup = InetAddress.getByName(addr);
        // verifica che l'indirizzo passato come argomento sia valido
        if (!this.dateGroup.isMulticastAddress())
            throw new IllegalArgumentException();
        this.port = port;
    }

    public void start() {
        MulticastSocket mcSkt;
        DatagramPacket packet;
        byte[] buffer;

        try (MulticastSocket multicastDate = new MulticastSocket(this.port+1)) {

            multicastDate.joinGroup(this.dateGroup);
            for (int i = 0; i < 10; i++) {
                buffer = new byte[BUFF_SIZE];
                packet = new DatagramPacket(buffer, BUFF_SIZE);
                multicastDate.receive(packet);
                System.out.println("Messaggio n." + (i+1) + ":\t" + new String(packet.getData()).trim());
            }
            multicastDate.leaveGroup(this.dateGroup);
        }
        catch (IOException e) { e.printStackTrace(); }
    }
}