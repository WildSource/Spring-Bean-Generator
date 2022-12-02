import generators.Controller;
import generators.Repository;
import generators.Service;
import picocli.CommandLine;

import javax.sound.sampled.Control;

/**
 * Starting point. If you want to understand the code I recommend being familiar with how picocli works
 */
public class Application {
    public static void main(String[] args) {
        /**
         * CommandLine parses the class that is feeded in.
         * The class feeded in is the command chosen and the thread executes the run method from the Runnable interface
         * with the arguments.
         */
        int exitCode = new CommandLine(new Controller()).execute("-h");
        System.exit(exitCode);
    }
}
