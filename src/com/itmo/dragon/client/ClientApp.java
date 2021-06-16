package com.itmo.dragon.client;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Клиентский модуль должен в интерактивном режиме считывать команды
 *
 * @author Francisco Estrella
 * @version 0.1
 */
public class ClientApp {
    private static Communication communication;

    public static Communication getCommunication() {
        return communication;
    }

	
    public static void main(String[] args) {
        if (initializeCommunication())
            CommandHandler.handleConsoleCommand();
    }

    private static Boolean initializeCommunication() {
        String serverAddress = "10.100.80.38";
        Integer port = 7077;
        Scanner scanner = new Scanner(System.in);
        do {
            try {
                communication = new Communication(serverAddress, port);
                return true;
            } catch (SocketException e) {
                e.printStackTrace();
                System.out.println("There was an exception while connecting to the server. " + e.getMessage());

            } catch (UnknownHostException e) {
                e.printStackTrace();
                System.out.println("There was an unknown exception. " + e.getMessage());
            }
            System.out.println("Would you like to try again (yes/no)?");
        } while (scanner.nextLine() == "yes");
        return false;
    }
}

