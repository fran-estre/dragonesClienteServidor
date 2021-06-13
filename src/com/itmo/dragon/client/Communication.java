package com.itmo.dragon.client;

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
        DatagramPacket datagramPacket = new DatagramPacket(command, command.length, serverAddress, port);
        this.datagramSocket.send(datagramPacket);
    }
}
