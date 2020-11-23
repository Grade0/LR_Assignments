package com.assign5;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class FilePathBuffer {
    /**
     * Overview: struttura dati Thread-safe per salvare i filepath delle directory
     */

    public LinkedList<String> filePathBuffer;
    public ReentrantLock lockQueue;
    public Condition isNotEmpty;

    public FilePathBuffer() {
        filePathBuffer = new LinkedList<>();
        lockQueue = new ReentrantLock();
        isNotEmpty = lockQueue.newCondition();
    }

    public void insertPath(String newFilePath){
        lockQueue.lock();
        try {
            filePathBuffer.addLast(newFilePath);
            isNotEmpty.signalAll();
        }
        finally {
            lockQueue.unlock();
        }
    }

    public String getPath(){
        lockQueue.lock();
        try {
            while (filePathBuffer.size() == 0) {
                try {
                    isNotEmpty.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String result = filePathBuffer.pollFirst();
            return result;
        }
        finally {
            lockQueue.unlock();
        }

    }
}
