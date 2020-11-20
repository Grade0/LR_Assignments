package com.assign2;

import java.util.concurrent.*;

public class SalaSportelli {
    private final int nSportelli = 4;
    private int clientiServiti = 0;
    private final ExecutorService threadPoolSportelli;

    /**
     *
     * @param k capienza sala sportelli (oltre agli sportelli),
     *          ovvero dimensione ArrayBlockingQueue
     */
    public SalaSportelli(int k) {
        this.threadPoolSportelli = new ThreadPoolExecutor(
                nSportelli,
                nSportelli,
                0L,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(k)
        );
    }

    //entrata nella sala sportelli (aka secondaSala)
    //se almeno uno sportello è libero viene servito subito,
    //altrimenti viene messo in attesa nella coda
    public void serviCliente(Cliente s) {
        try {
            this.threadPoolSportelli.execute(s);
        } catch (RejectedExecutionException e) {
            throw new RejectedExecutionException();
        }
        clientiServiti++;
    }


    public int getClientiServiti() {
        return clientiServiti;
    }

    //chiude la sala sportelli (aka secondaSala)
    //ovvero shutdown del threadPool
    public void chiudiSala(long timeout) {
        this.threadPoolSportelli.shutdown();
        try {
            while(!this.threadPoolSportelli.isTerminated()) {
                this.threadPoolSportelli.awaitTermination(timeout, TimeUnit.SECONDS);
            }
            System.out.println("\n --- Gli sportelli sono ora chiusi --- \n");
        } catch (InterruptedException e) {
            System.out.println("awaitTermination interrotta");
        }
    }
}
