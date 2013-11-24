package pl.edu.agh.dropper;

import org.apache.commons.cli.*;

public class App
{
    static final Options OPTIONS = buildOptions();

    public static void main( String[] args )
    {
        try {
            CommandLine cmd = new BasicParser().parse(OPTIONS, args);
            new AppDispatcher(cmd).dispatch();
        } catch (ParseException e) {
            System.out.println("Invalid arguments. Run the program with -h to find out more.");
        }
    }

    private static Options buildOptions(){
        Options options = new Options();
        options.addOption("h", "help", false, "Displays this help info.");
        return options;
    }
}
