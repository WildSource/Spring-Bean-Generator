import generators.TopCommand;
import picocli.CommandLine;

/**
 * Starting point. If you want to understand the code I recommend being familiar with how picocli works
 */
public class Application {
    public static void main(String[] args) {
        CommandLine commandLine = new CommandLine(new TopCommand());
        if (args.length == 0) {
            commandLine.execute("-h");
        } else {
            commandLine.execute(args);
        }

    }

    /*
    public static void main(String[] args) {
        CommandLine commandLine = new CommandLine(new TopCommand());
        int status = commandLine.execute("controller", "ComicController", "ComicService");
        System.exit(status);
    }
     */
}
