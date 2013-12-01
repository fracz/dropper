package pl.edu.agh.dropper;


import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import pl.edu.agh.dropper.proxy.RootProxy;

public class AppDispatcher {
    private final CommandLine cmd;

    public AppDispatcher(CommandLine cmd) {
        this.cmd = cmd;
    }

    public void dispatch() {
        if (cmd.hasOption('h'))
            displayHelp();
        else {
            String type = cmd.getOptionValue('t');
            try {
                RootProxy proxy = new RootProxy(type);
                proxy.setOptions(cmd);
                proxy.start();
            } catch (Exception e) {
                if (cmd.hasOption('v')) {
                    e.printStackTrace();
                } else {
                    System.out.println("There was an unexpected error. Try -v option.");
                }
            }
        }
    }

    private void displayHelp() {
        new HelpFormatter().printHelp("Dropper", DropperOptions.get());
    }
}
