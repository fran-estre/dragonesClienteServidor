package com.itmo.dragon.server;

import java.io.IOException;
import java.net.SocketException;

/**
 * Серверный модуль должен осуществлять выполнение команд по управлению коллекцией.
 *
 * @author Francisco Estrella
 * @version 0.1
 */
public class ServerApp {
    public static void main(String[] args) {
        Integer port = 7077;
        try {
            Communication communication = new Communication(port);
            communication.listen();
        } catch (SocketException e) {
            e.printStackTrace();
            System.out.println("There was an unknown exception." + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("There was an unknown exception." + e.getMessage());
        }

    }
}
