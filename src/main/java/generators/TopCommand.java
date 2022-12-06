package generators;

import picocli.CommandLine;

@CommandLine.Command(
        name = "TopCommand",
        subcommands = {
                Controller.class,
                Repository.class,
                Service.class
        },
        description = "This is just the starting point of the cli tool",
        mixinStandardHelpOptions = true,
        version = {"1.0.0"}
)
public class TopCommand implements Runnable{
        @Override
        public void run() {
        }
}
