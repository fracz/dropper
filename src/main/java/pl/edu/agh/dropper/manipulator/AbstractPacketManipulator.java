package pl.edu.agh.dropper.manipulator;

import pl.edu.agh.dropper.proxy.Packet;
import pl.edu.agh.dropper.proxy.PacketManipulator;

import java.util.Random;

import static com.google.common.base.Preconditions.checkArgument;

public abstract class AbstractPacketManipulator implements PacketManipulator {

    private final int probability;

    private final Random random = new Random(System.currentTimeMillis());

    public AbstractPacketManipulator(double probability) {
        checkArgument(probability >= 0 && probability <= 1, "Probability must be between 0 and 1 inclusive.");
        this.probability = (int) (probability * 100);
    }

    @Override
    public final Packet manipulate(Packet packet) {
        if (shouldManipulate()) {
            return doManipulation(packet);
        } else {
            return packet; // no modification
        }
    }

    protected abstract Packet doManipulation(Packet packet);

    private boolean shouldManipulate() {
        int rand = Math.abs(random.nextInt(100));
        return probability > rand;
    }
}