package pl.edu.agh.dropper.proxy;

import com.google.common.collect.ImmutableList;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.lang.StringUtils;
import pl.edu.agh.dropper.manipulator.BandwidthManipulator;
import pl.edu.agh.dropper.manipulator.ChangingManipulator;
import pl.edu.agh.dropper.manipulator.DroppingManipulator;
import pl.edu.agh.dropper.manipulator.MixingManipulator;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Class responsible for forwarding the packets.
 */
public class RootProxy {

    private final Proxy proxy;

    private DestinationSender destinationSender;

    private SourceSender sourceSender;

    private CommandLine options;

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
        this.options = options;
        try {
            setSourcePort();
            setDestinationAddress();
            setDestinationPort();
            describe();
        } catch (Exception e) {
            throw new ProxyException(e);
        }
    }

    private void describe() {
        System.out.println(String.format("Redirects traffic to %s:%s", options.getOptionValue('a'), options.getOptionValue('d')));
        if (options.hasOption('b'))
            System.out.println("Simulated bandwidth: " + Integer.valueOf(options.getOptionValue('b')) + " kb/s");
        if (options.hasOption("drop"))
            System.out.println("Packets drop: " + (Math.floor(getProbability("drop") * 100) + "%"));
        if (options.hasOption("mix"))
            System.out.println("Packets order mix: " + (Math.floor(getProbability("mix") * 100) + "%"));
        if (options.hasOption("change"))
            System.out.println("Packets content change: " + (Math.floor(getProbability("change") * 100) + "%"));
    }

    private void setDestinationAddress() throws UnknownHostException {
        String addressValue = options.getOptionValue('a');
        InetAddress address = InetAddress.getByName(addressValue);
        proxy.setDestinationAddress(address);
    }

    public void start() {
        destinationSender = new DestinationSender();
        sourceSender = new SourceSender();
        proxy.startProxy();
        destinationSender.start();
        sourceSender.start();
    }

    private boolean isVerbose() {
        return options.hasOption('v');
    }

    private void setSourcePort() throws NumberFormatException {
        String portValue = options.getOptionValue('s');
        int port = Integer.valueOf(portValue);
        System.out.println("Proxy listens on port: " + port);
        proxy.setSourcePort(port);
    }

    private void setDestinationPort() throws NumberFormatException {
        String portValue = options.getOptionValue('d');
        int port = Integer.valueOf(portValue);
        proxy.setDestinationPort(port);
    }

    private List<PacketManipulator> getManipulators() {
        ImmutableList.Builder<PacketManipulator> manipulators = new ImmutableList.Builder<>();
        if (options.hasOption('b')) {
            int bandwidth = Integer.valueOf(options.getOptionValue('b'));
            manipulators.add(new BandwidthManipulator(bandwidth));
        }
        if (options.hasOption("drop")) {
            double probability = getProbability("drop");
            manipulators.add(new DroppingManipulator(probability));
        }
        if (options.hasOption("mix")) {
            double probability = getProbability("mix");
            manipulators.add(new MixingManipulator(probability));
        }
        if (options.hasOption("change")) {
            double probability = getProbability("change");
            manipulators.add(new ChangingManipulator(probability));
        }
        return manipulators.build();
    }

    private double getProbability(String paramName) {
        String propValue = options.getOptionValue(paramName);
        return Double.valueOf(propValue);
    }

    private abstract class AbstractSender extends Thread {
        protected final List<PacketManipulator> manipulators;

        AbstractSender() {
            manipulators = getManipulators();
        }

        protected Packet manipulatePacket(Packet packet) {
            Packet manipulated = packet;
            for (PacketManipulator manipulator : manipulators) {
                if (manipulated == null)
                    break;
                manipulated = manipulator.manipulate(packet);
            }
            return manipulated;
        }
    }

    private class DestinationSender extends AbstractSender {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                Packet packet = proxy.receiveSource();
                if (isVerbose())
                    System.out.println(String.format("Received packet from source (%d bytes): %s",
                            packet.getData().length, packet.toString()));
                packet = manipulatePacket(packet);
                if (packet != null)
                    proxy.sendDestination(packet);
            }
        }
    }

    private class SourceSender extends AbstractSender {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                Packet packet = proxy.receiveDestination();
                if (isVerbose())
                    System.out.println(String.format("Received packet from destination (%d bytes): %s",
                            packet.getData().length, packet.toString()));
                packet = manipulatePacket(packet);
                if (packet != null)
                    proxy.sendSource(packet);
            }
        }
    }
}
