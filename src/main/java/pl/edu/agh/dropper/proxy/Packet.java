package pl.edu.agh.dropper.proxy;

import java.util.Objects;

/**
 * Data packet implementation. Base data unit for proxy.
 */
public class Packet {
    private byte[] data;

    public Packet(byte[] data) {
        setData(data);
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Packet)
            return Objects.equals(((Packet) obj).data, data);
        return false;
    }
}
