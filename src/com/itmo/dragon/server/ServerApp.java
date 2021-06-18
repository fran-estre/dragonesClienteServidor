package com.itmo.dragon.server;

import com.itmo.dragon.shared.entities.Dragon;

import java.io.File;
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
    public static Hashtable<Long, Dragon> dragonsHashtable = new Hashtable<Long, Dragon>();
    private static String fileName = "C:\\DragonData.xml";
    private static String initialization;

    public static String getFileName() {
        return fileName;
    }

    public static String getInitialization() {
        return initialization;
    }

    public static void setInitialization(String initialization) {
        ServerApp.initialization = initialization;
    }

    public static void main(String[] args) {
        if (args.length>0 && isValidFilename(args[0]))
            fileName = args[0];

        Integer port = 7077;
        try {
            Communication communication = new Communication(port);
            communication.listen();
        } catch (SocketException e) {
            e.printStackTrace();
            System.out.println("There was an unknown exception." + e.getMessage());
        }
    }

    private static boolean isValidFilename(String filename) {
        File file = new File(filename);
        return file.exists();
    }
}
