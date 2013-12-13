package pl.edu.agh.dropper.proxy;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.lang.StringUtils;
import pl.edu.agh.dropper.manipulator.BandwidthManipulator;
import pl.edu.agh.dropper.manipulator.ChangingManipulator;
import pl.edu.agh.dropper.manipulator.DroppingManipulator;
import pl.edu.agh.dropper.manipulator.MixingManipulator;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class responsible for forwarding the packets.
 */
public class RootProxy {

    private final Proxy proxy;

    private final List<PacketManipulator> manipulators = Collections.synchronizedList(new ArrayList<PacketManipulator>());

    private final DestinationSender destinationSender = new DestinationSender();

    private final SourceSender sourceSender = new SourceSender();

    public RootProxy(String proxyType) throws ProxyException {
        try {
            if (StringUtils.isEmpty(proxyType)) {
                proxyType = ProxyType.UDP.toString();
            }
            ProxyType type = ProxyType.valueOf(proxyType.toUpperCase());
            this.proxy = type.getProxy();
        } catch (IllegalArgumentException e) {
            throw new ProxyException(e);
        }
    }

    public void setOptions(CommandLine options) throws ProxyException {
        try {
            setSourcePort(options);
            setDestinationPort(options);
            setDestinationAddress(options);
            initializeManipulators(options);
        } catch (Exception e) {
            throw new ProxyException(e);
        }
    }

    private void setDestinationAddress(CommandLine options) throws UnknownHostException {
        String addressValue = options.getOptionValue('a');
        InetAddress address = InetAddress.getByName(addressValue);
        proxy.setDestinationAddress(address);
    }

    public void start() {
        proxy.startProxy();
        destinationSender.start();
        sourceSender.start();
    }

    private void setSourcePort(CommandLine options) throws NumberFormatException {
        String portValue = options.getOptionValue('s');
        int port = Integer.valueOf(portValue);
        proxy.setSourcePort(port);
    }

    private void setDestinationPort(CommandLine options) throws NumberFormatException {
        String portValue = options.getOptionValue('d');
        int port = Integer.valueOf(portValue);
        proxy.setDestinationPort(port);
    }

    private void initializeManipulators(CommandLine options) {
        if (options.hasOption('b'))
            manipulators.add(new BandwidthManipulator(64 * 1024));
        if (options.hasOption("drop"))
            manipulators.add(new DroppingManipulator(getProbability(options, "drop")));
        if (options.hasOption("mix"))
            manipulators.add(new MixingManipulator(getProbability(options, "mix")));
        if (options.hasOption("change"))
            manipulators.add(new ChangingManipulator(getProbability(options, "change")));
    }

    private double getProbability(CommandLine options, String paramName) {
        String propValue = options.getOptionValue(paramName);
        return Double.valueOf(propValue);
    }

    private Packet manipulatePacket(Packet packet) {
        Packet manipulated = packet;
        for (PacketManipulator manipulator : manipulators) {
            if (manipulated == null)
                break;
            manipulated = manipulator.manipulate(packet);
        }
        return manipulated;
    }

    private class DestinationSender extends Thread {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                Packet packet = proxy.receiveSource();
                packet = manipulatePacket(packet);
                if (packet != null)
                    proxy.sendDestination(packet);
            }
        }
    }

    private class SourceSender extends Thread {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                Packet packet = proxy.receiveDestination();
                packet = manipulatePacket(packet);
                if (packet != null)
                    proxy.sendSource(packet);
            }
        }
    }
}
