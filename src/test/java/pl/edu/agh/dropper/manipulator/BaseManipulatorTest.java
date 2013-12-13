package pl.edu.agh.dropper.manipulator;

import org.junit.Before;
import pl.edu.agh.dropper.proxy.Packet;

import java.util.Random;

public abstract class BaseManipulatorTest {
    private final Random random = new Random(System.currentTimeMillis());

    protected final Packet[] packets = new Packet[3];

    @Before
    public void initializeDefaultPackets() {
        for (int i = 0; i < packets.length; i++) {
            byte data[] = new byte[Math.abs(random.nextInt(255)) + 1];
            random.nextBytes(data);
            packets[i] = new Packet(data);
        }
    }
}
