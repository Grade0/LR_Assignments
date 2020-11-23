package com.assign5;

import java.io.File;

public class Producer implements Runnable {

    private final FilePathBuffer pathBuffer;
    private final String initialPath;
    private final int consumersNumber;
    private final String endSearch;

    public Producer(String newInitialPath, FilePathBuffer newPathBuffer, int newConsumersNumber, String newEndSearch){
        this.pathBuffer = newPathBuffer;
        this.initialPath = newInitialPath;
        this.consumersNumber = newConsumersNumber;
        this.endSearch = newEndSearch;
    }

    public void run() {
        //controllo directory già effettuato nel main

        //inserimento directory nel FilePathBuffer
        pathBuffer.insertPath(initialPath);

        //visita ricorsiva di tutte le sottodirectory
        crawl(initialPath);

        //inserimento della Stringa di terminazione
        stopConsumers();
    }

    //Metodo che ricorsivamente visita tutte le sottodirectory partendo dalla directory passato come paramentro
    //e lo inserisce nel FilePathBuffer
    private void crawl(String pathName){
        File currentFile = new File(pathName);
        File[] fileList;
        String currentCanonicalPath;
        try {
            fileList = currentFile.listFiles();
            for (File thisFile : fileList) {
                currentCanonicalPath = thisFile.getCanonicalPath();
                if (thisFile.isDirectory()) {
                    pathBuffer.insertPath(currentCanonicalPath);
                    crawl(currentCanonicalPath);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    //Metodo per l'inserimento della Stringa speciale di terminazione
    //per ogni singolo Consumatori
    private void stopConsumers(){
        int i;
        for(i = 0; i < consumersNumber; i++){
            pathBuffer.insertPath(endSearch);
        }
    }


}
