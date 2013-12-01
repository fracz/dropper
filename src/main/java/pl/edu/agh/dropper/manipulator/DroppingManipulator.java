package pl.edu.agh.dropper.manipulator;

import pl.edu.agh.dropper.proxy.Packet;

/**
 * Manipulator for packets that drops them.
 */
public class DroppingManipulator extends AbstractPacketManipulator {
    public DroppingManipulator(double probability) {
        super(probability);
    }

    @Override
    protected Packet doManipulation(Packet packet) {
        return null;
    }
}
