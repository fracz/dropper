package pl.edu.agh.dropper.manipulator;

import org.junit.Test;
import pl.edu.agh.dropper.proxy.Packet;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class ChangingManipulatorTest extends BaseManipulatorTest {

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
