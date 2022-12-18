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
        Repository repository = new Repository();


       new Thread(repository, "repositoryThread").start();
       new Thread(new Service(), "serviceThread").start();
       new Thread(new Controller(), "controllerThread").start();
    }
}
