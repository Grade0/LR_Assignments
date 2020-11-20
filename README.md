# LR_Assignments
"Network Programming" Class Assignments - University of Pisa

## Assignment 01 - PiGreco

Scrivere un programma che attiva un thread T che effettua il calcolo approssimato di π. 

Il programma principale riceve in input da linea di comando un parametro che indica il grado di accuratezza (accuracy) per il calcolo di π ed il tempo massimo di attesa dopo cui il programma principale interrompe il thread T.

Il thread T effettua un ciclo infinito per il calcolo di π usando la serie di Gregory-Leibniz (π = 4/1 – 4/3 + 4/5 - 4/7 + 4/9 - 4/11 ...).

Il thread esce dal ciclo quando una delle due condizioni seguenti risulta verificata:

1. il thread è stato interrotto

2. la differenza tra il valore stimato di π ed il valore Math.PI (della libreria JAVA) è minore di accuracy
<br>

## Assignment 02 - Ufficio Postale

Simulare il flusso di clienti in un ufficio postale che ha 4 sportelli. Nell'ufficio esiste:

* un'ampia sala d'attesa in cui ogni persona può entrare liberamente. Quando entra, ogni persona prende il numero dalla numeratrice e aspetta il proprio turno in questa sala.
* una seconda sala, meno ampia, posta davanti agli sportelli, in cui possono essere presenti al massimo k persone (oltre alle persone servite agli sportelli)
* una persona si mette quindi prima in coda nella prima sala, poi passa nella seconda sala.
* ogni persona impiega un tempo differente per la propria operazione allo sportello. Una volta terminata l'operazione, la persona esce dall'ufficio
 
Scrivere un programma in cui:

* l'ufficio viene modellato come una classe JAVA, in cui viene attivato un ThreadPool di dimensione uguale al numero degli sportelli
* la coda delle persone presenti nella sala d'attesa è gestita esplicitamente dal programma
* la seconda coda (davanti agli sportelli) è quella gestita implicitamente dal ThreadPool
* ogni persona viene modellata come un task, un task che deve essere assegnato ad uno dei thread associati agli sportelli
* si preveda di far entrare tutte le persone nell'ufficio postale, all'inizio del programma
