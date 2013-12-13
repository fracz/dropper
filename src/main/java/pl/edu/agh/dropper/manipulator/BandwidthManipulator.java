package pl.edu.agh.dropper.manipulator;

import pl.edu.agh.dropper.proxy.Packet;
import pl.edu.agh.dropper.proxy.PacketManipulator;

/**
 * Packet manipulator that simulates slow internet connection.
 */
public class BandwidthManipulator implements PacketManipulator {

    private final int bps;

    /**
     * @param kbps kilobits per second (speed)
     */
    public BandwidthManipulator(int kbps) {
        this.bps = kbps * 1024;
    }

    @Override
    public Packet manipulate(Packet packet) {
        long bits = packet.getData().length * 8;
        long delay = bits * 1000 / bps;
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return packet;
    }
}
