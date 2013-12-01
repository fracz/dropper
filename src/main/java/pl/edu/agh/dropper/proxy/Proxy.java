package pl.edu.agh.dropper.proxy;

import java.net.InetAddress;

public interface Proxy {
    /**
     * Receive next packet sent to the monitored application.
     *
     * @return packet data that was received from monitored application
     */
    Packet receiveSource();

    /**
     * Receives next packet sent from the destination machine to the monitored application (destination response).
     *
     * @return packet data that was received from destination machine
     */
    Packet receiveDestination();

    /**
     * Sends the given packet to the monitored application.
     *
     * @param packet packet to send
     */
    void sendSource(Packet packet);

    /**
     * Sends the given packet to the destination address.
     *
     * @param packet packet to send
     */
    void sendDestination(Packet packet);

    /**
     * Sets the local port to listen.
     *
     * @param port port to listen for incoming data
     */
    void setSourcePort(int port);

    /**
     * Sets the remote port where the data should be forwarded.
     *
     * @param port port where the incoming data should be forwarded
     */
    void setRemotePort(int port);

    /**
     * Sets the remote address where the data should be forwarded.
     *
     * @param address address where the incoming data should be forwarded
     */
    void setRemoteAddress(InetAddress address);
}
