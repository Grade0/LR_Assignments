package com.assign8;

public class TestMain {

    /**
     *
     * Test server in un scenario
     */

    public static void main(String[] args) throws InterruptedException {
        Thread serverThread = new Thread(new EchoServer());
        serverThread.setName("EchoServer");
        serverThread.start();

        new Thread(new EchoClient("Hello, I'm a client")).start();
        new Thread(new EchoClient("Long String to echo")).start();
        new Thread(new EchoClient("blah blah blah")).start();
        new Thread(new EchoClient("Some more echoes")).start();
        new Thread(new EchoClient("Hello World!")).start();
        new Thread(new EchoClient("Try to echo me...")).start();
        serverThread.join();
    }
}

