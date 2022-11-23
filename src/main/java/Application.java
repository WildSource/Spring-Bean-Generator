import generators.Controller;
import generators.Repository;
import picocli.CommandLine;

public class Application {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new Repository()).execute("ComicRepository");
        System.exit(exitCode);
    }
}
