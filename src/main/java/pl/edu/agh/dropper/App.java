package pl.edu.agh.dropper;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

public class App
{
    public static void main( String[] args )
    {
        try {
            CommandLine cmd = new BasicParser().parse(DropperOptions.get(), args);
            new AppDispatcher(cmd).dispatch();
        } catch (ParseException e) {
            System.out.println("Invalid arguments. Run the program with -h to find out more.");
        }
    }
}
