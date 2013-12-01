package pl.edu.agh.dropper.proxy;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.lang.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Class responsible for forwarding the packets.
 */
public class RootProxy {

    private final Proxy proxy;

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
}
