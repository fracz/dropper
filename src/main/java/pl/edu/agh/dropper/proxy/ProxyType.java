package pl.edu.agh.dropper.proxy;

/**
 * Enumerates supported proxy types.
 */
public enum ProxyType {
    UDP(null);

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