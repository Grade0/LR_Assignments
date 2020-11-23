package com.assign7;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class MainClass {
    public static void main(String[] args) {
        int myPort = 6789;
        boolean running = true;
        ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
        System.out.println ("Web server waiting for request on port " + myPort + "...\n");
        try {
            ServerSocket listenSocket = new ServerSocket(myPort);
            do {
                //Apertura e accettazione della connessione
                Socket connectionSocket = listenSocket.accept();
                //si crea una task per la connessione arrivata e la si passa alla threadpool
                threadPool.execute(new Handler(connectionSocket));
            } while (running);
            threadPool.shutdown();
            try { threadPool.awaitTermination(1, TimeUnit.SECONDS); }
            catch (InterruptedException ignored) {}
            //Chiusura della connessione
            listenSocket.close();
        } catch (IOException e) { e.printStackTrace(); }
    }
}
