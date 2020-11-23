package com.assign7;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Date;


public class Handler implements Runnable {
    private Socket connectionSocket;

    public Handler(Socket sckt) {
        this.connectionSocket = sckt;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + "\tNew connection from " + connectionSocket.getInetAddress().getHostAddress());
            //Si prende da InputStream la request message HTTP
            InputStream inStream = connectionSocket.getInputStream();
            String HTTPRequest = new String(inStream.readNBytes(100));
            int indexOfFirstNewLine = HTTPRequest.indexOf("\n");
            //Si prende la prima riga del request message(la request line)
            //lo spezza e si prende il path
            //si toglie il simbolo "/" da inizio path
            String pathRequested = HTTPRequest.substring(0, indexOfFirstNewLine).split(" ")[1].substring(1);
            System.out.println(Thread.currentThread().getName() + "\tRequested: " + pathRequested);

            File file = new File(pathRequested);
            OutputStream outStream = connectionSocket.getOutputStream();
            try {
                byte[] fileContents = Files.readAllBytes(Paths.get(pathRequested));
                outStream.write(("HTTP/1.1 200 OK\n").getBytes(Charset.defaultCharset()));
                outStream.write(("Server: Davide's HTTPFileTransferServer v1.0\n").getBytes(Charset.defaultCharset()));
                outStream.write(("Date: " + new Date().toString() + "\n").getBytes(Charset.defaultCharset()));
                outStream.write(("Content-Type: text/plain\n").getBytes(Charset.defaultCharset()));
                outStream.write(("Content-Length: " + file.length() + "\n").getBytes(Charset.defaultCharset()));
                outStream.write(("\n").getBytes(Charset.defaultCharset()));
                outStream.write(fileContents);
                //Response message e body con il file in caso esista
                outStream.flush();
            } catch (NoSuchFileException ex) {
                outStream.write(("HTTP/1.1 500 No Such File Exception\n").getBytes(Charset.defaultCharset()));
                outStream.write(("Server: Davide's HTTPFileTransferServer v1.0\n").getBytes(Charset.defaultCharset()));
                outStream.write(("Date: " + new Date().toString() + "\n").getBytes(Charset.defaultCharset()));
                //La response message d'errore in caso di file non esistente
                outStream.flush();
            }

            System.out.println(Thread.currentThread().getName() + " Closing connection");
            connectionSocket.close();
        } catch (IOException e) { e.printStackTrace(); }
    }
}
