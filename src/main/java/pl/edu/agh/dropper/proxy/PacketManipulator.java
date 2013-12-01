package pl.edu.agh.dropper.proxy;

/**
 * Type definition for packet manipulators.
 */
public interface PacketManipulator {
    /**
     * Manipulates the given packet.
     *
     * @param packet packet to manipulate
     * @return manipulated packet that should be sent to the destination or {@code null} if the packet should not be sent
     */
    Packet manipulate(Packet packet);
}
