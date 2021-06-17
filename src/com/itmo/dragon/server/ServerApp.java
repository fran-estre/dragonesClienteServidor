package com.itmo.dragon.server;

import com.itmo.dragon.shared.entities.Dragon;

import java.io.IOException;
import java.net.SocketException;
import java.util.Hashtable;

/**
 * Серверный модуль должен осуществлять выполнение команд по управлению коллекцией.
 *
 * @author Francisco Estrella
 * @version 0.1
 */
public class ServerApp {
    static Hashtable<Long, Dragon> dragonsHashtable = new Hashtable<Long, Dragon>();
    static String initialization;
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
