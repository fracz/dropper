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
        byte[] data = packet.getData();
        int bytesToChange = random.nextInt(data.length);
        byte[] bytes = new byte[bytesToChange];
        random.nextBytes(bytes);
        for (byte b : bytes)
            data[random.nextInt(data.length)] = b;
        Packet modified = new Packet(data);
        return modified;
    }
}
