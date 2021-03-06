package pl.edu.agh.dropper;

import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

public class DropperOptions {
    private static final Options OPTIONS = buildOptions();

    private static Options buildOptions() {
        Options options = new Options();
        options.addOption(OptionBuilder
                .hasArg()
                .withArgName("udp")
                .withLongOpt("type")
                .withDescription("Type of the connection to proxy. Default to UDP.")
                .create('t'));
        options.addOption(OptionBuilder
                .hasArg()
                .withArgName("port")
                .withLongOpt("source")
                .withDescription("Source port that the application will connect to.")
                .isRequired()
                .create('s'));
        options.addOption(OptionBuilder
                .hasArg()
                .withArgName("port")
                .withLongOpt("destination")
                .withDescription("Destination port that the data should be proxied to.")
                .isRequired()
                .create('d'));
        options.addOption(OptionBuilder
                .hasArg()
                .withArgName("address")
                .withLongOpt("address")
                .withDescription("Address of the host that the data should be proxied to.")
                .isRequired()
                .create('a'));
        options.addOption(OptionBuilder
                .hasArg()
                .withArgName("0-1")
                .withDescription("The probability of packets drop.")
                .create("drop"));
        options.addOption(OptionBuilder
                .hasArg()
                .withArgName("0-1")
                .withDescription("The probability of packets order mix.")
                .create("mix"));
        options.addOption(OptionBuilder
                .hasArg()
                .withArgName("0-1")
                .withDescription("The probability of packets content change.")
                .create("change"));
        options.addOption(OptionBuilder
                .hasArg()
                .withArgName("kbps")
                .withDescription("Simulated bandwidth in kilobytes per second.")
                .withLongOpt("bandwidth")
                .create('b'));
        options.addOption("v", "verbose", false, "Turns on the verbose mode.");
        options.addOption("h", "help", false, "Displays this help info.");
        return options;
    }

    public static Options get() {
        return OPTIONS;
    }
}
