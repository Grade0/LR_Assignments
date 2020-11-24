package com.assign8;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class EchoClient implements Runnable{
    private String strToEcho;

    public static void main(String[] args) {
        if (args.length >= 1) new Thread(new EchoClient(args[0])).start();
        else new Thread(new EchoClient()).start();
    }

    /**
     * Costruttore di default: il client chiederà in input da console la stringa da spedire
     */
    public EchoClient() {
        this.strToEcho = null;
    }

    /**
     * Costruttore per testing: il client spedirà {@code strToEcho} al server echo
     * @param strToEcho La stringa da spedire al server echo
     */
    public EchoClient(String strToEcho) {
        this.strToEcho = strToEcho;
    }

    @Override
    public void run() {
        try {
            if (this.strToEcho == null) {
                //lettura del messaggio da inviare da console
                Scanner in = new Scanner(System.in);
                System.out.print("String to be echoed: ");
                strToEcho = in.next();
            }

            //apertura SocketChannel e connessione con il server
            SocketChannel connectionSocket = SocketChannel.open();
            System.out.println("Connecting...");
            connectionSocket.connect(new InetSocketAddress("127.0.0.1", 6789));
            System.out.println("Connected. Sending \"" + strToEcho + "\"...");

            ByteBuffer myBuffer = ByteBuffer.allocate(128);
            myBuffer.put(strToEcho.getBytes());

            //Scrittura del messaggio sul canale per l'invio al server
            while(myBuffer.hasRemaining()) {
                try {
                    connectionSocket.write(myBuffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            myBuffer.clear();

            System.out.println("Data sent. Awating echo...");
            String bufferOutput = "";

            //Lettura dal canale del messaggio inviato dal server
            while(connectionSocket.read(myBuffer) > 0) {
                myBuffer.flip();
                bufferOutput += StandardCharsets.UTF_8.decode(myBuffer).toString();
                myBuffer.compact();
            }

            System.out.println("Messaggio ricevuto: \"" + bufferOutput + "\"");
            System.out.println("Disconected from EchoServer");

            //chiusura della connessione
            connectionSocket.close();
        } catch (IOException ignored) {}
    }
}