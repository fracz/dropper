package pl.edu.agh.dropper.proxy.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;

public class UDPProxyThread extends Thread {
    private final DatagramSocket socket;
    private final InetAddress remoteAddr;
    private final int remotePort;
    private HashMap<String, DatagramSocket> remoteSockets;
    private HashMap<String, UDPClientThread> remoteThreads;
    private UDPProxy server;
    private boolean running;

    public UDPProxyThread(UDPProxy server, DatagramSocket datagramSocket, InetAddress remoteAddress, int remotePort) {
        this.server = server;
        this.socket = datagramSocket;
        this.remoteAddr = remoteAddress;
        this.remotePort = remotePort;
        this.remoteSockets = new HashMap<String, DatagramSocket>();
        this.remoteThreads = new HashMap<String, UDPClientThread>();
        this.running = true;
    }

    /**
     * Odbiera pakiet, ktory przyszedl na lokalny port, a nastepnie go przekazuje na zdefiniowany wczesniej adres/port zdalny
     */
    public void run() {
        while (isRunning()) {
            byte[] buf = new byte[1500]; //TODO: sprawdzic jaka wartosc tutaj wsadzic
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
                proxy(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (UDPClientThread thread : remoteThreads.values()) {
            thread.finish();
        }
    }

    /**
     * Przekazuje pakiet do zdefiniowanego w konstruktorze zdalnego hosta. Jesli jest to pierwszy pakiet
     * otrzymany od danego klienta, to tworzy dla niego nowy socket, za pomoca ktorego beda wysylane pakiety
     * do zdalnego hosta oraz nowy watek, obslugjacy odpowiedzi (pakiety przychodzace 'z zewnatrz')
     *
     * @param packet
     * @throws IOException
     */
    private void proxy(DatagramPacket packet) throws IOException {
        String hashString = packet.getAddress().toString() + ":" + packet.getPort();
        DatagramSocket remoteSocket = remoteSockets.get(hashString);
        if (remoteSocket == null) {
            remoteSocket = new DatagramSocket();
            UDPClientThread clientThread = new UDPClientThread(this.server, socket, remoteSocket,
                    packet.getAddress(), packet.getPort());
            clientThread.start();
            remoteSockets.put(hashString, remoteSocket);
            remoteThreads.put(hashString, clientThread);
        }
        byte[] buf = packet.getData();
        DatagramPacket proxyPacket = new DatagramPacket(buf, buf.length, this.remoteAddr, this.remotePort);
        remoteSocket.send(proxyPacket);
    }

    synchronized private boolean isRunning() {
        return running;
    }

    synchronized public void finish() {
        running = false;
    }
}
