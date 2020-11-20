package com.assign2;

import java.util.LinkedList;
import java.util.concurrent.RejectedExecutionException;

public class UfficioPostale {
    public static void main(String[] args) {
        //flusso di clienti, variabile tra 30 a 80
        final int nClienti = (int) (Math.random() * 50) + 30;
        //capienza massima seconda sala, oltre alle persone servite agli sportelli
        final int k = 10;

        System.out.println("\n\n<<< Apertura UFFICIO POSTALE Main >>>");

        //creazione della prima sala
        LinkedList<Cliente> filaPrimaSala = new LinkedList<Cliente>();

        //ogni cliente prende il proprio numerino
        //e si mette in fila nella sala comune (aka PrimaSala)
        for(int i = 1; i <= nClienti; i++) {
            Cliente c = new Cliente(i);
            filaPrimaSala.add(c);
        }

        System.out.println("\nNella sala comune sono presenti " + nClienti + " clienti");
        System.out.println("\n --- Gli sportelli sono ora aperti --- \n");

        SalaSportelli secondaSala = new SalaSportelli(k);

        //finché la fila nella prima sala comune (aka primaSala) non è esaurita
        //continuo a farla scorrere verso la sala sportelli (aka secondaSala)
        //se quest'ultimo è pieno, ritento finché non si libera un posto
        while(filaPrimaSala.size() > 0) {
            try {
                //provo ad aggiungere la task nella threadPool senza toglierlo dal LinkedList
                //ovvero provo a verificare che ci sia posto nella sala sportelli (aka secondaSala)
                //prima di "entrarci"
                secondaSala.serviCliente(filaPrimaSala.peek());
                //si toglie la task dalla LinkedList
                //ovvero il cliente si è spostato nella sala sportelli (aka secondaSala)
                filaPrimaSala.poll();
            } catch (RejectedExecutionException e) {
                continue;
            }
        }
        //finito di servire tutti i clienti, chiudo la sala sportelli
        secondaSala.chiudiSala(5);

        //stampo resoconto della giornata
        System.out.println("Totale clienti arrivati oggi nell'ufficio postle: " + nClienti);
        System.out.println("Totale clienti serviti agli sportelli: " + secondaSala.getClientiServiti());

        for(int i = 0; i < 8; i++) {
            System.out.printf("Professore n. %d\n", i+1);
        }
    }
}

