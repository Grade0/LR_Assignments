## Assignment 01 - Pi (π)

Write a program that activates a thread T that performs the approximate calculation of π.

The main program receives as input from the command line a parameter that indicates the degree of accuracy for the calculation of π and the maximum waiting time after which the main program interrupts the thread T.

Thread T performs an infinite loop for calculating π using the Gregory-Leibniz series (π = 4/1 – 4/3 + 4/5 - 4/7 + 4/9 - 4/11 ...).

The thread exits the loop when one of the following two conditions is verified:

* the thread was interrupted

* the difference between the estimated value of π and the Math.PI value (from the JAVA library) is less than the accuracy
<br>

## Assignment 02 - Post Office

Simulate the flow of customers in a post office that has 4 counters. In the office there are:

* a large waiting room where everyone can enter freely. When entering, each person takes the number from the numbering machine and waits for their turn in this room.

* a second room, smaller, located in front of the counters, in which can be present at most k people (in addition to the people served at the counters)

* one person then queues first in the first room, then passes into the second room.

* each person takes a different time for their operation at the counter. Once the operation is over, the person leaves the office;

Write a program where:

* the office is modeled as a JAVA class, in which a ThreadPool of a size equal to the number of counters is activated

* the queue of the people in the waiting room is managed explicitly by the program

* the second queue (in front of the counters) is the one managed implicitly by the Threadpool

* each person is modeled as a task, a task that must be assigned to one of the threads associated with the counters

* all'inizio del programma è previsto che tutte le persone entrino nell'ufficio postale

* let all people enter the post office, at the beginning of the program.
<br>

## Assignment 03 - Management of a computer lab

The Polo Marzotto's computer lab is used by three types of users, students, thesis students and professors, and each user must request the tutor to access the laboratory. The computers in the laboratory are numbered from 1 to 20. Access requests are different depending on the type of user:

* the professors have exclusive access to the whole laboratory, as they need to use all the computers to carry out tests on the network.

* undergraduates require the exclusive use of a single computer, identified by index i, since a particular software necessary for the development of the thesis is installed on that computer.

* Students require the exclusive use of any computer.

The professors have priority over everyone in the access to the laboratory, the thesis students have priority over the students.

Nobody can be interrupted while using a computer. Write a JAVA program that simulates user and tutor behavior. The program receives as input the number of students, thesis students and professors who use the laboratory and activates a thread for each user. Each user accesses k times to the lab, with k generated randomly. Simulate the time interval between one access and the next one and the time spent in the laboratory using the sleep method. The tutor has to coordinate access to the lab. The program must end when all users have completed their access to the laboratory.    
<br>

## Assignment 04 - Computer lab with Monitor

Solve the problem of the computer lab simulation, of the previous assignment, using the Monitor construct.    
<br>

## Assignment 05 - File Crawler

Write a JAVA program that:

* receives as input a file-path which identifies a directory D

* prints the information of the contents of that directory and, recursively, of all the files contained in the subdirectories of D

The program must be structured as follows:

* activates a producer thread and a set of k consumer threads

* the producer communicates with consumers through a queue

*the producer recursively visits the given directory and eventually all subdirectories and queues the name of each directory detected.

* consumers take directory names from the queue and print their content (file names)

* the queue must be created with a LinkedList. Remember that a Linked List is not a thread-safe structure. From the JAVA API: *“Note that the implementation is not synchronized. If multiple threads access a linked list concurrently, and at least one of the threads modifies the list structurally, it must be synchronized externally”*
<br>

## Assignment 06 - Bank accounts

* Create a file containing objects representing a bank's checking accounts. Each checking account contains the name of the account holder and a list of transactions. The transactions recorded for a checking account are related to the last 2 years, so they can be very numerous.

	* for each transaction, the date and reason for payment are recorded.
	* The set of possible reasons are fixed: Bank transfer, Credit, Bulletin, F24, PagoBancomat.
	* NB: Write a program that creates the file

* Write a program that re-reads the file and finds, for any possible reason for payment, how many movements have that reason.

	* design an application that activates a set of threads. One of them reads from the file the objects "ContoCorrente" (checking account) and passes them, one at a time, to the threads present in a thread pool.
	* each thread calculates the number of occurrences of each possible reason for payment within that checking account and updates a global counter.
	* at the end the program prints the total number of occurrences for each possible reason.

* Use NIO for file interaction and JSON for serialization
<br>

## Assignment 07 - Mini Web Server

Write a JAVA program that implements an HTTP server that handles requests for transferring files of different types (eg jpeg images, gif) coming from a web browser.

The server

* listens on a port known to the client (e.g. 6789)
* handles HTTP GET requests to the Request URL localhost: port/filename

Further indications

* connections can be non-persistent.
* use the Socket and ServerSocket classes to develop the server program
* to send the requests to the server, use any browser
<br>

## Assignment 08 - NIO Echo Server

Write an echo server program using the java NIO library and, in particular, the Selector and channels in non-blocking mode, and an echo client program, using NIO (also fine with blocking mode).

* The server accepts requests for connections from clients, receives messages sent by clients and sends them back (possibly adding "echoed by server" to the received message)

* The client reads the message to be sent from the console, sends it to the server and displays what it has received from the server.

<br>

![Description](/image_assign8.png)

<br>

## Assignment 09 - UDP Ping

PING is a network performance evaluation utility used to verify the reachability of a host over an IP network and to measure round trip time (RTT) for messages sent from a sending host to a destination host.

The purpose of this assignment is to implement a PING server and a corresponding PING client that allows the client to measure its RTT to the server.

The functionality provided by these programs must be similar to that of the PING utility available in all modern operating systems. The fundamental difference is that UDP is used for communication between client and server, instead of ICMP (Internet Control Message Protocol).

Also, since the execution of the programs will run on a single host or on the local network and in both cases both the latency and the loss of packages are negligible, the server must introduce an artificial delay and ignore some requests to simulate the loss of packages

### PING Client

* accepts two command-line arguments: **server name and port**. If one or more arguments are incorrect, the client terminates after printing an error message like *ERR -arg x*, where x is the argument number

* uses UDP communication to communicate with the server and sends 10 messages to the server, with the following format:

       PING seqno timestamp
    where *seqno* is the PING sequence number (between 0-9) and the *timestamp* (in milliseconds) indicates when the message was sent

* does not send a new PING until it has received the echo of the previous PING, or a timeout has expired

* Print each message sent to the server and the ping RTT or a * if the response was not received within 2 seconds

* After receiving the tenth response (or after its timeout), the client prints a summary similar to the one printed by the UNIX PING

                  ---- PING Statistics ----  
      10 packets transmitted, 7 packets received, 30% packet loss  
      round-trip (ms) min/avg/max = 63/190.29/290

* the average RTT is printed with 2 digits after the decimal point

### PING Server

* is essentially an echo server: sends back to the sender any data received

* accepts a command-line argument: the port, which is the one the server is running on. If any of the arguments are incorrect, print an error message of the type *ERR -arg x*, where x is the argument number

* after receiving a PING, the server determines whether to ignore the packet (simulating its loss) or echo it. The default probability of packet loss is 25%

* if it decides to echo the PING, the server waits for a random amount of time to simulate network latency

* prints the IP address and port of the client, the PING message and the action taken by the server following its reception (PING not sent, or PING delayed by x ms)
<br>

## Assignment 10 - UDP Time Server

Define a *TimeServer* server, which:

* sends the date and time to a multicast group *dategroups* at regular intervals
* waits between one sending and the next a time interval simulated by the sleep () method

The IP address of *dategroup* is introduced by the command-line.

Then define a *TimeClient* client that joins *dategroup* and receives, ten consecutive times, date and time, displays them, then terminates.    
<br>

## Assignment 11 - Congress management

Design a Client/Server application for managing registrations at a congress. The organization of the congress provides the speakers of the various sessions with an interface through which to subscribe to a session, and the possibility of viewing the programs of the various days of the congress, with the interventions of the various sessions. The server maintains the programs of the 3 days of the congress, each of which is stored in a data structure, in which each line corresponds to a session (in total 12 for each day). For each session, the names of the registered speakers (maximum 5) are stored.

The client can request operations to:

* register a speaker at a session
* get the program of the congress

The client forwards requests to the server via the **RMI** mechanism. Provide, for each possible operation, the management of any abnormal conditions (for example the request for registration to a non-existent day and/or session or for which all the intervention spaces have already been covered).

The client is implemented as a cyclic process that continues making synchronous requests until all user needs are exhausted. Establish an appropriate termination condition for the request process.

It's okay to run the client, server, and registry on the same host.
