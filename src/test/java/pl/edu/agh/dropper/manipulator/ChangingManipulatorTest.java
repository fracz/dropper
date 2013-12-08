package pl.edu.agh.dropper.manipulator;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.dropper.proxy.Packet;

import java.util.Random;

import static org.junit.Assert.assertThat;

;

public class ChangingManipulatorTest {
    private final Random random = new Random(System.currentTimeMillis());

    private Packet[] packets = new Packet[100];

    @Before
    public void initializeDefaultPackets() {
        for (int i = 0; i < packets.length; i++) {
            byte data[] = new byte[Math.abs(random.nextInt(255))];
            random.nextBytes(data);
            packets[i] = new Packet(data);
        }
    }

    @Test
    public void testPacketManipulation() {
        ChangingManipulator manipulator = new ChangingManipulator(1.0);
        Packet[] result = new Packet[packets.length];
        for (int i = 0; i < packets.length; i++) {
            result[i] = manipulator.manipulate(packets[i]);
        }
        assertThat(result, IsNot.not(IsEqual.equalTo(packets)));
    }
}
