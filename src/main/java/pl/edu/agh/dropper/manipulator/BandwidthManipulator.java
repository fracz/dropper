package pl.edu.agh.dropper.manipulator;

import pl.edu.agh.dropper.proxy.Packet;
import pl.edu.agh.dropper.proxy.PacketManipulator;

/**
 * Packet manipulator that simulates slow internet connection.
 */
public class BandwidthManipulator implements PacketManipulator {
    @Override
    public Packet manipulate(Packet packet) {
        // TODO implement some sleeping based on the packet size and the bandwidth set
        return packet;
    }
}
