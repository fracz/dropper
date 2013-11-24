package pl.edu.agh.dropper;


import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;

public class AppDispatcher {
    private final CommandLine cmd;

    public AppDispatcher(CommandLine cmd){
        this.cmd = cmd;
    }

    public void dispatch() {
        if(cmd.hasOption('h'))
            displayHelp();
    }

    private void displayHelp() {
        new HelpFormatter().printHelp("Dropper", DropperOptions.get());
    }
}
