import generators.Controller;
import picocli.CommandLine;

public class Application {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new Controller()).execute(args);
        System.exit(exitCode);
    }
}
