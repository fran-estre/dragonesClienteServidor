package com.itmo.dragon.server;

import com.itmo.dragon.shared.commands.Command;
import com.itmo.dragon.shared.commands.SerializationHandler;
import com.itmo.dragon.shared.commands.SizeMessage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.*;

public class Communication {
    static final int SIZE = 512;
    static final int HEADER = 128; // I guess
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
            byte[] buffer = new byte[SIZE+HEADER];
            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
            try {
                datagramSocket.receive(datagramPacket);
                SizeMessage sizeMessage = (SizeMessage) SerializationHandler.deserialize(datagramPacket.getData());
                if (sizeMessage.Size <= 0) continue;

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int repetition = getRepetition(sizeMessage);
                for (int i = 0; i < repetition; i++) {
                    datagramSocket.receive(datagramPacket);
                    bos.write(datagramPacket.getData());
                }
                buffer = bos.toByteArray();

                Command command = (Command) SerializationHandler.deserialize(buffer);
                String response = processHandler.processCommand(command);
                AnswerToClient(datagramPacket, response);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("There was an exception while receiving the datagramPacket " + e.getMessage());
                datagramSocket.close();
            }
        }
    }

    private int getRepetition(SizeMessage sizeMessage) {
        int residue = sizeMessage.Size % SIZE;
        int division = (sizeMessage.Size - residue) / SIZE;

        return residue == 0 ? division : division + 1;
    }

    private void AnswerToClient(DatagramPacket datagramPacket, String response) throws IOException {
        byte[] responseBytes = response.getBytes();
        InetAddress clientAddress = datagramPacket.getAddress();
        int clientPort = datagramPacket.getPort();
        DatagramPacket responsePacket = new DatagramPacket(responseBytes, responseBytes.length, clientAddress, clientPort);
        datagramSocket.send(responsePacket);
    }
}
