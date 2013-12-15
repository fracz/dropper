package pl.edu.agh.dropper.proxy;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Objects;

/**
 * Data packet implementation. Base data unit for proxy.
 */
public class Packet {
    private byte[] data;
    private InetAddress fromAddr;
    private InetAddress toAddr;
    private int fromPort;
    private int toPort;

    public Packet(byte[] data) {
        setData(data);
    }

    public Packet(DatagramPacket dp) {
        setData(dp.getData());
        setSourceAddr(dp.getAddress());
        setSourcePort(dp.getPort());
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setSourceAddr(InetAddress addr) { this.fromAddr = addr; }

    public InetAddress getSourceAddr() { return fromAddr; }

    public void setDestinationAddr(InetAddress addr) { this.toAddr = addr; }

    public InetAddress getDestinationAddr() { return toAddr; }

    public void setSourcePort(int port) { this.fromPort = port; }

    public int getSourcePort() { return fromPort; }

    public void setDestinationPort(int port) { this.toPort = port; }

    public int getDestinationPort() { return toPort; }

    @Override
    public int hashCode() {
        return data.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Packet)
            return Objects.equals(((Packet) obj).data, data);
        return false;
    }

    public String toString() {
        return String.format("Packet\tfrom: %s:%d\tto: %s:%d", fromAddr.toString(), fromPort, toAddr.toString(), toPort);
    }
}
