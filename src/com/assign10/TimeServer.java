package com.assign10;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class TimeServer {
    //l'indirizzo del gruppo di multicast
    private InetAddress multicastGroup;
    //porta associata all'indirizzo multicast
    private final int port;

    /**
     *
     * @param addr indirizzo del gruppo di multicast
     * @param port porta a cui associare il socket di multicast
     * @throws UnknownHostException se l'indirizzo non è valido
     * @throws IllegalArgumentException se l'indirizzo non è un indirizzo di multicast
     */
    public TimeServer(String addr, int port) throws UnknownHostException, IllegalArgumentException {
        this.multicastGroup = InetAddress.getByName(addr);
        if (!this.multicastGroup.isMulticastAddress())
            throw new IllegalArgumentException();
        this.port = port;
    }

    /**
     * avvia il server
     */
    public void start() {
        DatagramPacket packet;
        byte[] buffer;

        try(DatagramSocket socket = new DatagramSocket(this.port);) {

            while (true) {
                DateFormat format = new SimpleDateFormat("dd/MM/yyy HH.mm.ss");
                Date date = Calendar.getInstance().getTime();
                buffer = format.format(date).getBytes(StandardCharsets.UTF_8);
                packet = new DatagramPacket(buffer, buffer.length, this.multicastGroup, this.port + 1);
                socket.send(packet);
                System.out.printf(
                        "Il server ha inviato %s\n",
                        new String(packet.getData(), packet.getOffset(), packet.getLength())
                );
                try { Thread.sleep(1000); }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}