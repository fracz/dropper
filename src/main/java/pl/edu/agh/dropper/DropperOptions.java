package pl.edu.agh.dropper;

import org.apache.commons.cli.Options;

public class DropperOptions {
    private static final Options OPTIONS = buildOptions();

    private static Options buildOptions() {
        Options options = new Options();
        options.addOption("h", "help", false, "Displays this help info.");
        options.addOption("v", "verbose", false, "Turns on the verbose mode.");
        return options;
    }

    public static Options get() {
        return OPTIONS;
    }
}
