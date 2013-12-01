package pl.edu.agh.dropper.proxy.udp;


import pl.edu.agh.dropper.proxy.Packet;
import pl.edu.agh.dropper.proxy.Proxy;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class UDPProxy implements Proxy {
    private ArrayList<UDPProxyThread> threads;
    
    public UDPProxy() {
        threads = new ArrayList<UDPProxyThread>();        
    }
    
    /**
     * Dodaje mapowanie z lokalnego portu na zdalny adres/port. Uruchamia watek, ktory odbiera pakiety przychodzace
     * na lokalny port i przesyla je dalej na adres/port zdalny.
     * @param localPort
     * @param remoteAddress
     * @param remotePort
     * @throws SocketException
     */
    public void addProxyMapping(int localPort, InetAddress remoteAddress, int remotePort) throws SocketException {
        DatagramSocket ds = new DatagramSocket(localPort);
        UDPProxyThread thread = new UDPProxyThread(this, ds, remoteAddress, remotePort);
        thread.start();
        threads.add(thread);
    }

    /**
     * Zatrzymuje wszystkie watki
     */
    public void stopServer() {
        for(UDPProxyThread thread : threads) {
            thread.finish();
        }
    }

    @Override
    public Packet receiveSource() {
        return null;
    }

    @Override
    public Packet receiveDestination() {
        return null;
    }

    @Override
    public void sendSource(Packet packet) {

    }

    @Override
    public void sendDestination(Packet packet) {

    }

    @Override
    public void setSourcePort(int port) {

    }

    @Override
    public void setDestinationPort(int port) {

    }

    @Override
    public void setDestinationAddress(InetAddress address) {

    }

    @Override
    public void startProxy() {
    }
}
