package com.assign8;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;


public class EchoServer implements Runnable {
    static String message = "";
    static boolean running = true;

    @Override
    public void run() {

        try (
                //Apertura del socket di ascolto
                ServerSocketChannel listenSocket = ServerSocketChannel.open()
        ) {

            //Configurazione del socket
            listenSocket.socket().bind(new InetSocketAddress(6789));

            //Configurazione del socket come non bloccante
            listenSocket.configureBlocking(false);

            //Creazione del selettore
            Selector selector = Selector.open();

            //Si specifica l'operazione d'interesse per il socket al selettore
            listenSocket.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println(Thread.currentThread().getName() + ": server online, waiting for new connections...");

            while(running) {
                try {
                    selector.select();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //insieme delle chiavi corrispondenti a canali pronti
                Set<SelectionKey> selectedKeys = selector.selectedKeys();

                //iteratore dell'insieme sopra definito
                Iterator<SelectionKey> iter = selectedKeys.iterator();

                while(iter.hasNext()) {
                    SelectionKey key = iter.next();
                    iter.remove();
                    if(key.isAcceptable()) {
                        this.acceptNewConnection(selector, key);
                    }
                    if(key.isReadable()) {
                        this.readRequest(selector, key);
                    }
                    if(key.isWritable()) {
                        this.writeRequest(key);
                    }
                }
            }

            System.out.println(Thread.currentThread().getName() + ": server shutting down");
            //Chiusura della connessione
            listenSocket.close();
            System.out.println(Thread.currentThread().getName() + ": server offline");
        } catch (IOException e) { e.printStackTrace(); }
    }

    /**
     * procedura per l'accettazione di nuove connessioni
     *
     * @param selettore il selettore a cui registrare il canale con l'operazione di READ
     * @param key chiave del canale pronto per l'operazione di ACCEPT
     * @throws IOException
     */
    private void acceptNewConnection(Selector selettore, SelectionKey key) throws IOException {
        ServerSocketChannel server = (ServerSocketChannel) key.channel();
        SocketChannel c_channel = server.accept();
        c_channel.configureBlocking(false);
        //crea il buffer di dimensione 256 bytes
        ByteBuffer myBuffer = ByteBuffer.allocate(128);
        // aggiunge il canale del client al selector con l'operazione OP_WRITE
        // e aggiunge il bytebuffer come attachment
        c_channel.register(selettore, SelectionKey.OP_READ, myBuffer);
        System.out.println("Accettata nuova connessione dal client: " + c_channel.getRemoteAddress());
    }


    /**
     * procedura per la lettura da un canale
     *
     * @param selettore il selettore a cui registrare il canale con l'operazione di WRITE
     * @param key chiave del canale pronto per l'operazione di READ
     * @throws IOException
     */
    private void readRequest(Selector selettore, SelectionKey key) throws ClosedChannelException {
        SocketChannel c_channel = (SocketChannel) key.channel();
        ByteBuffer myBuffer = (ByteBuffer) key.attachment();
        String bufferOutput = null;
        try {
            myBuffer.clear();
            while(c_channel.read(myBuffer) > 0) {
                myBuffer.flip();
                bufferOutput = StandardCharsets.UTF_8.decode(myBuffer).toString();
                myBuffer.compact();
                message += bufferOutput;
            }
        } catch (IOException e)  {
            e.printStackTrace();
        }
        System.out.println("Messaggio ricevuto dal client: " + bufferOutput);
        if (message.equals("exit")) {
            message = "Server: 'exit' keyword riceived, EchoServer shutted down!";
            EchoServer.shutdownServer();
        } else {
            message = "Echoed by EchoServer: " + message;
        }
        myBuffer.put(message.getBytes());
        c_channel.register(selettore, SelectionKey.OP_WRITE, myBuffer);
        message = "";
    }


    /**
     * procedura per la scrittura su un canale
     *
     * @param key chiave del canale pronto per l'operazione di WRITE
     * @throws IOException
     */
    private void writeRequest(SelectionKey key) throws IOException {
        SocketChannel c_channel = (SocketChannel) key.channel();
        ByteBuffer myBuffer = (ByteBuffer) key.attachment();

        myBuffer.flip();
        while(myBuffer.hasRemaining()) {
            c_channel.write(myBuffer);
        }
        myBuffer.clear();
        System.out.println("Messaggio echo inviato e connessione chiusa col client: " + c_channel.getRemoteAddress());
        c_channel.close();
    }


    /**
     * Avvia la procedura di chiusura del server
     */
    public static void shutdownServer() {
        running = false;
    }

    /**
     * main da cui far partire EchoServer (alternativa a TestMain)
     */
    public static void main(String[] args) {
        Thread server = new Thread(new EchoServer());
        server.setName("EchoServer");
        server.start();
    }
}