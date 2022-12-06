import generators.TopCommand;
import picocli.CommandLine;

/**
 * Starting point. If you want to understand the code I recommend being familiar with how picocli works
 */
public class Application {
    public static void main(String[] args) {
        new CommandLine(new TopCommand())
                .execute(args);

    }
}
