package com.assign8;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Federico Matteoni
 */
public class EchoClientHandler implements Runnable {
    private SocketChannel connectionSocket;

    public EchoClientHandler(SocketChannel sckt) {
        this.connectionSocket = sckt;
    }

    @Override
    public void run() {
        ByteBuffer myBuffer = ByteBuffer.allocate(256);
        StringBuilder sBuff = new StringBuilder();
        String str;
        SelectionKey keyR, keyW;

        System.out.println(Thread.currentThread().getName() + "\tConnected");                       //Handler online

        try {
            connectionSocket.configureBlocking(false);
            Selector selector = Selector.open();
            keyR = connectionSocket.register(selector, SelectionKey.OP_READ);
            keyW = connectionSocket.register(selector, SelectionKey.OP_WRITE);                      //Preparo i canali

            int n;
            do {
                myBuffer.clear();
                n = ((SocketChannel) keyR.channel()).read(myBuffer);
            } while (n == 0);  //Attesa


            do {
                n = ((SocketChannel) keyR.channel()).read(myBuffer);
            } while (n > 0);                  //Lettura
            myBuffer.flip();
            sBuff.append(StandardCharsets.UTF_8.decode(myBuffer).toString());
            str = sBuff.toString();                                                                 //Costruzione String

            //if (str.equals("exit")) EchoServer.shutdownServer();                                    //Close on "exit"

            System.out.println(Thread.currentThread().getName() + "\t\"" + str + "\"");             //Output

            str = str.concat("\t(echoed by Fexed's Echo Server)");                                  //Preparazione echo
            myBuffer = ByteBuffer.wrap(str.getBytes(StandardCharsets.UTF_8));
            do { n = ((SocketChannel) keyW.channel()).write(myBuffer); }  while(n > 0);                //Invio echo

        } catch (IOException ignored) {}

        System.out.println(Thread.currentThread().getName() + "\tDisconnected");                    //Handler offline
    }
}