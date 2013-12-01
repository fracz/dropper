package pl.edu.agh.dropper.proxy.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClientThread extends Thread {
    private final DatagramSocket remoteSocket;
    private final DatagramSocket clientSocket;
    private final InetAddress address;
    private final int port;
    private final UDPProxy server;
    private boolean running;

    public UDPClientThread(UDPProxy server, DatagramSocket clientSocket, DatagramSocket remoteSocket,
                           InetAddress clientAddr, int clientPort) {
        this.server = server;
        this.clientSocket = clientSocket;
        this.remoteSocket = remoteSocket;
        this.address = clientAddr;
        this.port = clientPort;
        this.running = true;
    }

    /**
     * Odbiera pakiety przychodzace 'z zewnatrz' (response) i przesyla je do odpowiedniego klienta
     */
    public void run() {
        while (isRunning()) {
            byte[] buf = new byte[1500]; //TODO: sprawdzic jaka wartosc tutaj wsadzic
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                remoteSocket.receive(packet);
                byte[] buffer = packet.getData();
                DatagramPacket proxyPacket = new DatagramPacket(buffer, buffer.length, this.address, this.port);
                clientSocket.send(proxyPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    synchronized private boolean isRunning() {
        return running;
    }

    synchronized public void finish() {
        running = false;
    }

}
