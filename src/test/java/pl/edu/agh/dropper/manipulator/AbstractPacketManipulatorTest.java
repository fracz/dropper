package pl.edu.agh.dropper.manipulator;

import org.junit.Test;
import pl.edu.agh.dropper.proxy.Packet;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class AbstractPacketManipulatorTest {
    private final Random random = new Random(System.currentTimeMillis());

    @Test
    public void testNeverCallingWhenProbabilityIsZero() {
        class NeverManipulator extends AbstractPacketManipulator {
            public NeverManipulator() {
                super(0.0);
            }

            @Override
            protected Packet doManipulation(Packet packet) {
                fail("The manipulator were called.");
                throw new AssertionError();
            }
        }
        AbstractPacketManipulator manipulator = new NeverManipulator();
        for (int i = 0; i < 1000; i++)
            manipulator.manipulate(new Packet(new byte[3]));
    }

    @Test
    public void testAlwaysCallingWhenProbabilityIsOne() {
        final AtomicInteger counter = new AtomicInteger(0);
        class AlwaysManipulator extends AbstractPacketManipulator {
            public AlwaysManipulator() {
                super(1.0);
            }

            @Override
            protected Packet doManipulation(Packet packet) {
                counter.incrementAndGet();
                return packet;
            }
        }
        AbstractPacketManipulator manipulator = new AlwaysManipulator();
        int loops = 1000;
        for (int i = 0; i < loops; i++)
            manipulator.manipulate(new Packet(new byte[3]));
        assertEquals(loops, counter.get());
    }

    @Test
    public void testSometimesCallingWhenProbabilityIsHalfOne() {
        final AtomicInteger counter = new AtomicInteger(0);
        class SometimesManipulator extends AbstractPacketManipulator {
            public SometimesManipulator() {
                super(0.5);
            }

            @Override
            protected Packet doManipulation(Packet packet) {
                counter.incrementAndGet();
                return packet;
            }
        }
        AbstractPacketManipulator manipulator = new SometimesManipulator();
        int loops = 1000;
        for (int i = 0; i < loops; i++)
            manipulator.manipulate(new Packet(new byte[3]));
        assertThat(counter.get(), is(greaterThan(0)));
        assertThat(counter.get(), is(lessThan(loops)));
    }
}
