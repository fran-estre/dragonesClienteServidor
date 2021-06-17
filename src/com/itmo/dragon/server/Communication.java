package com.itmo.dragon.server;

import com.itmo.dragon.shared.commands.Command;
import com.itmo.dragon.shared.commands.SerializationHandler;

import java.io.IOException;
import java.net.*;

public class Communication {
    private Integer port;
    private DatagramSocket datagramSocket;

    public Communication(Integer port) throws SocketException {
        this.port = port;
        datagramSocket = new DatagramSocket(port);
    }

    public void listen() {
        try {
            System.out.println("Server working at" + InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println("There was an exception while getting local host address " + e.getMessage());
        }
        while (true) {
            byte[] buffer = new byte[1024];
            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
            try {
                datagramSocket.receive(datagramPacket);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("There was an exception while receiving the datagramPacket " + e.getMessage());
            }
            Command command = (Command) SerializationHandler.deserialize(datagramPacket.getData());
            // depending of the command name do what you need to do
            Process.рrocessCommand(command);
        }
    }
}