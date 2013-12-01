package pl.edu.agh.dropper.proxy;

import pl.edu.agh.dropper.proxy.udp.UDPProxy;

/**
 * Enumerates supported proxy types.
 */
public enum ProxyType {
    UDP(UDPProxy.class);

    private final Class<? extends Proxy> proxyClass;

    private ProxyType(Class<? extends Proxy> proxyClass) {
        this.proxyClass = proxyClass;
    }

    public Proxy getProxy() throws ProxyException {
        try {
            return proxyClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ProxyException(e);
        }
    }
}