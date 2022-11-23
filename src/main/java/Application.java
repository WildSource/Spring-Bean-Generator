import generators.Controller;
import picocli.CommandLine;

public class Application {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new Controller()).execute("AccountController", "AccountService", "--post","-g", "-r", "-m=/v1/entity");
        System.exit(exitCode);
    }
}
