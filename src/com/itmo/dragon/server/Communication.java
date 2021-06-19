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

        ProcessHandler processHandler = new ProcessHandler();
        while (true) {
            byte[] buffer = new byte[1024];
            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
            try {
                datagramSocket.receive(datagramPacket);
                Command command = (Command) SerializationHandler.deserialize(datagramPacket.getData());
                String response = processHandler.processCommand(command);
                AnswerToClient(datagramPacket, response);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("There was an exception while receiving the datagramPacket " + e.getMessage());
                datagramSocket.close();
            }
        }
    }

    private void AnswerToClient(DatagramPacket datagramPacket, String response) throws IOException {
        byte[] responseBytes = response.getBytes();
        InetAddress clientAddress = datagramPacket.getAddress();
        int clientPort = datagramPacket.getPort();
        DatagramPacket responsePacket = new DatagramPacket(responseBytes, responseBytes.length, clientAddress, clientPort);
        datagramSocket.send(responsePacket);
    }
}
