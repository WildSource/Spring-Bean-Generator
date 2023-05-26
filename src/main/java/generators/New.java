package generators;

import picocli.CommandLine;

@CommandLine.Command(
        name = "new",
        description = "generate a default controller, service and repository",
        mixinStandardHelpOptions = true,
        version = {"1.0.1"}
)
public class New extends Generator implements Runnable {

    @CommandLine.Parameters(
            description = {"name of the entity"}
    )
    String name;


    @Override
    public void run() {
        Repository repository = new Repository(name);
        repository.run();

        Service service = new Service(name);
        service.run();

        Controller controller = new Controller(name);
        controller.run();
    }
}
