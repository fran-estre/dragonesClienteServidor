package com.itmo.dragon.server;
import java.io.IOException;
import java.net.*;

public class Communication {
    private Integer port;
    private DatagramSocket datagramSocket;

    public Communication(Integer port) throws SocketException {
        this.port = port;
        datagramSocket = new DatagramSocket(port);
    }

    public void listen() throws IOException {
        System.out.println("Server working at" + InetAddress.getLocalHost());
        while (true) {
            byte[] buffer = new byte[1024];
            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
            datagramSocket.receive(datagramPacket);
            //to do procesar el datagrama y devolver la respuesta verificando a que cliente debe enviarse la respuesta
            System.out.println(datagramPacket.getData().toString());
        }
    }
}
