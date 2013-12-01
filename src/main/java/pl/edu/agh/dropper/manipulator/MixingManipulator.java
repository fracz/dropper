package pl.edu.agh.dropper.manipulator;

import pl.edu.agh.dropper.proxy.Packet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Packet manipulator that changes their order.
 */
public class MixingManipulator extends AbstractPacketManipulator {
    private final List<Packet> knownPackets = new ArrayList<Packet>();

    private final Random random = new Random(System.currentTimeMillis());

    public MixingManipulator(double probability) {
        super(probability);
    }

    @Override
    protected Packet doManipulation(Packet packet) {
        knownPackets.add(packet);
        if (canMix()) {
            return mix();
        }
        return null;
    }

    private boolean canMix() {
        return knownPackets.size() > 1;
    }

    private Packet mix() {
        int index = Math.abs(random.nextInt(knownPackets.size()));
        try {
            return knownPackets.get(index);
        } finally {
            knownPackets.remove(index);
        }
    }
}
