package com.assignm10;

import org.apache.commons.cli.*;
import java.net.UnknownHostException;

public class TimeClientMain {
    final static int DEFAULT_PORT = 30000;

    public static void __printArgError(int n) {
        System.err.printf("ERR - arg %d\n", n);
        System.exit(1);
    }

    public static void main(String[] args) {

        Options options = new Options();

        Option input = new Option("ip", true, "indirizzo IP di dateGroup");
        input.setRequired(true);
        options.addOption(input);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            formatter.printHelp("java TimeClientMain", options);
            System.exit(1);
        }

        String IPAddress = null;
        try {
            if (cmd.getOptionValue("ip") != null)
                IPAddress = cmd.getOptionValue("ip");
            else {
                __printArgError(1);
            }
        } catch (NumberFormatException e) {
            __printArgError(1);
        }
        try {
            TimeClient client = new TimeClient(IPAddress, DEFAULT_PORT);
            client.start();
        } catch (UnknownHostException e) {             // l'indirizzo passato non è valido
            System.err.println("L'indirizzo immesso non è valido");
        } catch (IllegalArgumentException e) {          // l'indirizzo passato non è un indirizzo multicast
            System.err.println("L'indirizzo immesso non è un indirizzo multicast");
        }
    }
}
