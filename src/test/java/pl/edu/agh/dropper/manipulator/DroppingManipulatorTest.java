package pl.edu.agh.dropper.manipulator;

import org.junit.Test;
import pl.edu.agh.dropper.proxy.Packet;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

public class DroppingManipulatorTest extends BaseManipulatorTest {

    @Test
    public void testPacketManipulation() {
        DroppingManipulator manipulator = new DroppingManipulator(0.8);
        List<Packet> notRemoved = new ArrayList<>();
        for (int i = 0; i < packets.length; i++) {
            Packet packet = manipulator.doManipulation(packets[i]);
            if (packet != null)
                notRemoved.add(packet);
        }
        assertThat(notRemoved.size(), lessThan(packets.length));
    }
}
