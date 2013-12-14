package pl.edu.agh.dropper;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;
import pl.edu.agh.dropper.proxy.RootProxy;

public class App {
    public static void main(String[] args) {
        CommandLine options = parseArgs(args);
        launch(options);
    }

    private static CommandLine parseArgs(String[] args) {
        CommandLine options;
        try {
            options = new BasicParser().parse(DropperOptions.get(), args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            displayHelp();
            System.exit(1);
            return null; // for compiler only
        }
        return options;
    }

    private static void launch(CommandLine options) {
        try {
            String type = options.getOptionValue('t');
            RootProxy proxy = new RootProxy(type);
            proxy.setOptions(options);
            proxy.start();
        } catch (Exception e) {
            if (options.hasOption('v')) {
                e.printStackTrace();
            } else {
                System.out.println("There was an unexpected error. Try -v option.");
            }
        }
    }

    private static void displayHelp() {
        new HelpFormatter().printHelp("Dropper", DropperOptions.get(), true);
    }
}
