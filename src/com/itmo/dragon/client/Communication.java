package com.itmo.dragon.client;

import com.itmo.dragon.shared.commands.SerializationHandler;
import com.itmo.dragon.shared.commands.SizeMessage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.*;

public class Communication {
    private Integer port;
    private DatagramSocket datagramSocket;
    private InetAddress serverAddress;

    public Communication(String serverAddress, Integer port) throws SocketException, UnknownHostException {
        this.port = port;
        datagramSocket = new DatagramSocket(this.port);
        this.serverAddress = InetAddress.getByName(serverAddress);
    }

    public void send(byte[] command) throws IOException {
        int repetition = SerializationHandler.getRepetition(command.length);
        SizeMessage sizeMessage = new SizeMessage();
        sizeMessage.Size = command.length;
        byte[] sizeBytes = SerializationHandler.serialize(sizeMessage);
        DatagramPacket sizePacket = new DatagramPacket(sizeBytes, sizeBytes.length, serverAddress, port);
        this.datagramSocket.send(sizePacket);

        int offset = 0;
        for (int i = 0; i < repetition; i++) {
            int partSize = SerializationHandler.SIZE * (i + 1) < command.length ? SerializationHandler.SIZE : command.length - SerializationHandler.SIZE * i;
            byte[] part = new byte[partSize];
            System.arraycopy(command, offset, part, 0, partSize);
            offset = offset + SerializationHandler.SIZE;
            DatagramPacket datagramPacket = new DatagramPacket(part, part.length, serverAddress, port);
            this.datagramSocket.send(datagramPacket);
        }
    }

    public String receive() {
        byte[] buf = new byte[SerializationHandler.SIZE+SerializationHandler.HEADER];
        DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);
        try {
            datagramSocket.receive(datagramPacket);
            return new String(datagramPacket.getData(), 0, datagramPacket.getLength());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
