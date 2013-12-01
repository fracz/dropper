package pl.edu.agh.dropper.proxy.udp;


import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * Klasa sluzaca do zarzadzania serwerem udp
 */
public class UDPProxy {
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

    /**
     * Ustawia procent pakietow, ktore maja byc zablokowane
     */
    public void setDropRate() {
        //TODO
    }

    /**
     * Ustawia maksymalna przepustowosc serwera udp
     */
    public void setBandwidth() {
        //TODO
    }

}
