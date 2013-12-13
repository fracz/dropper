package pl.edu.agh.dropper.manipulator;

import org.junit.Test;
import pl.edu.agh.dropper.proxy.Packet;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class BandwidthManipulatorTest {
    @Test
    public void testSlowConnection() {
        int kbps = 64;
        int packetSizeInBytes = 1500;
        int bitsPerSecond = kbps * 1024;
        long expectedDelayMs = packetSizeInBytes * 8 * 1000 / bitsPerSecond;

        BandwidthManipulator manipulator = new BandwidthManipulator(kbps);
        Packet packet = new Packet(new byte[packetSizeInBytes]);

        long start = System.currentTimeMillis();
        assertEquals(packet, manipulator.manipulate(packet));
        assertThat(System.currentTimeMillis(), greaterThanOrEqualTo(start + expectedDelayMs));
    }
}
