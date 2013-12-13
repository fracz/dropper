package pl.edu.agh.dropper.manipulator;

import org.junit.Test;
import pl.edu.agh.dropper.proxy.Packet;

import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class MixingManipulatorTest extends BaseManipulatorTest {

    @Test
    public void testPacketManipulation() {
        MixingManipulator manipulator = new MixingManipulator(0.8);
        Packet[] result = new Packet[packets.length];
        manipulator.manipulate(packets[0]); // must know the first packet to mix
        result[0] = packets[0];
        for (int i = 1; i < packets.length; i++) {
            result[i] = manipulator.manipulate(packets[i]);
        }
        assertThat(packets, not(arrayContaining(result)));
    }
}