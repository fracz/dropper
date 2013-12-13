package pl.edu.agh.dropper.manipulator;

import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.dropper.proxy.Packet;

import java.util.Random;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class ChangingManipulatorTest {
    private final Random random = new Random(System.currentTimeMillis());

    private Packet[] packets = new Packet[100];

    @Before
    public void initializeDefaultPackets() {
        for (int i = 0; i < packets.length; i++) {
            byte data[] = new byte[Math.abs(random.nextInt(255)) + 1];
            random.nextBytes(data);
            packets[i] = new Packet(data);
        }
    }

    @Test
    public void testPacketManipulation() {
        ChangingManipulator manipulator = new ChangingManipulator(0.8);
        Packet[] result = new Packet[packets.length];
        for (int i = 0; i < packets.length; i++) {
            result[i] = manipulator.manipulate(packets[i]);
        }
        assertThat(result, not(equalTo(packets)));
    }

    @Test
    public void testPacketNotManipulatedWhenProbabilityIsZero() {
        ChangingManipulator manipulator = new ChangingManipulator(0.0);
        Packet[] result = new Packet[packets.length];
        for (int i = 0; i < packets.length; i++) {
            result[i] = manipulator.manipulate(packets[i]);
        }
        assertThat(result, equalTo(packets));
    }
}
