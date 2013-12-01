package pl.edu.agh.dropper.proxy;

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
}
