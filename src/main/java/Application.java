import generators.Controller;
import generators.Repository;
import generators.Service;
import picocli.CommandLine;

public class Application {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new Controller()).execute("ComicController", "ComicService");
        System.exit(exitCode);
    }
}
