package pl.edu.agh.dropper.proxy.udp;


import pl.edu.agh.dropper.proxy.Packet;
import pl.edu.agh.dropper.proxy.Proxy;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class UDPProxy implements Proxy {
    private InetAddress destinationAddr;
    private int destinationPort;
    private int sourcePort;
    private DatagramSocket sourceSocket;
    private boolean running = false;

    private HashMap<String, DatagramSocket> destinationSockets;
    private BlockingQueue<Packet> receivedPackets;

    
    public UDPProxy() {
        destinationSockets = new HashMap<String, DatagramSocket>();
        receivedPackets = new ArrayBlockingQueue<Packet>(50);
    }

    @Override
    public Packet receiveSource() {
        try {
            byte[] buf = new byte[1500];
            DatagramPacket dpacket = new DatagramPacket(buf, buf.length);
            sourceSocket.receive(dpacket);
            Packet packet = new Packet(dpacket);
            packet.setDestinationAddr(destinationAddr);
            packet.setDestinationPort(destinationPort);
            return packet;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Packet receiveDestination() {
        if (running) {
            try {
                return receivedPackets.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void sendSource(Packet packet) {
        if (running) {
            try {
                DatagramPacket dp = new DatagramPacket(packet.getData(), packet.getData().length,
                        packet.getDestinationAddr(), packet.getDestinationPort());
                sourceSocket.send(dp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void sendDestination(Packet packet) {
        if (running) {
            try {
                DatagramSocket socket = getDestinationSocket(packet.getSourceAddr(), packet.getSourcePort());
                DatagramPacket dpacket = new DatagramPacket(packet.getData(), packet.getData().length,
                        packet.getDestinationAddr(), packet.getDestinationPort());
                socket.send(dpacket);
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setSourcePort(int port) {
        if (!running)
            sourcePort = port;
    }

    @Override
    public void setDestinationPort(int port) {
        if (!running)
            destinationPort = port;
    }

    @Override
    public void setDestinationAddress(InetAddress address) {
        if (!running)
            destinationAddr = address;
    }

    @Override
    public void startProxy() {
        if (!running) {
            try {
                sourceSocket = new DatagramSocket(sourcePort);
                running = true;
            } catch (SocketException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private DatagramSocket getDestinationSocket(InetAddress sourceAddr, int sourcePort) throws SocketException {
        String hashString = sourceAddr.toString() + ":" + sourcePort;
        DatagramSocket socket = destinationSockets.get(hashString);
        if (socket == null) {
            socket = new DatagramSocket();
            destinationSockets.put(hashString, socket);
            new DestinationReceiver(socket, sourceAddr, sourcePort).start();
        }
        return socket;
    }

    private class DestinationReceiver extends Thread {
        private DatagramSocket destinationSocket;
        private InetAddress sourceAddr;
        private int sourcePort;

        DestinationReceiver(DatagramSocket socket, InetAddress addr, int port) {
            destinationSocket = socket;
            sourceAddr = addr;
            sourcePort = port;
        }
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    byte[] buf = new byte[1500];
                    DatagramPacket dpacket = new DatagramPacket(buf, buf.length);
                    destinationSocket.receive(dpacket);
                    Packet packet = new Packet(dpacket);
                    packet.setDestinationAddr(sourceAddr);
                    packet.setDestinationPort(sourcePort);
                    receivedPackets.put(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
