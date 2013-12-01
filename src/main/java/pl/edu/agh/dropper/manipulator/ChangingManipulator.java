package pl.edu.agh.dropper.manipulator;

import pl.edu.agh.dropper.proxy.Packet;

/**
 * Packet manipulator that changes the packet content.
 */
public class ChangingManipulator extends AbstractPacketManipulator {
    public ChangingManipulator(double probability) {
        super(probability);
    }

    @Override
    protected Packet doManipulation(Packet packet) {
        // TODO implement
        return packet;
    }
}
