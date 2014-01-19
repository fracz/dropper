package pl.edu.agh.dropper.manipulator;

import pl.edu.agh.dropper.proxy.Packet;

import java.util.Random;

/**
 * Packet manipulator that changes the packet content.
 */
public class ChangingManipulator extends AbstractPacketManipulator {
    private final Random random = new Random(System.currentTimeMillis());

    public ChangingManipulator(double probability) {
        super(probability);
    }

    @Override
    protected Packet doManipulation(Packet packet) {
        packet.getData()[Math.abs(random.nextInt(3))] = 'X';
        return packet;
    }
}
