package com.assign5;

import java.io.File;

public class Consumer implements Runnable {

    private final FilePathBuffer pathBuffer;
    private final String endSearch;

    public Consumer(FilePathBuffer newPathBuffer, String newEndSearch){
        this.pathBuffer = newPathBuffer;
        this.endSearch = newEndSearch;
    }

    public void run() throws IllegalArgumentException {
        String targetFilePath;
        File fileToOpen;

        //Cicla finché il valore restituito dal metodo non è la String speciale di terminazione
        while ((targetFilePath = pathBuffer.getPath()) != endSearch) {
            fileToOpen = new File(targetFilePath);

            //non dovrebbe capitare mai, si controlla a scopo di debug
            if (!fileToOpen.isDirectory())
                throw new IllegalArgumentException("Invalid File: not a directory.");

            File[] filesList = fileToOpen.listFiles();
            printDirectoryContent(targetFilePath, filesList);
        }
    }

    //metodo che stampa i nomi di tutti i file presenti nella directory
    private static synchronized void printDirectoryContent(String parentDirectory, File [] filesList){
        System.out.println(Thread.currentThread().getName() + " ha aperto la directory situato in " + parentDirectory);
        System.out.println("[CONTENUTO DIRECTORY]");
        for(File currentFile : filesList){
            System.out.println(currentFile.getName());
        }
        System.out.println("[FINE CONTENT]");
        System.out.flush();
    }
}
