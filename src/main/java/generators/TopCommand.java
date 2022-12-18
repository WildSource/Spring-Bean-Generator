package generators;

import picocli.CommandLine;

@CommandLine.Command(
        name = "TopCommand",
        subcommands = {
                Controller.class,
                Repository.class,
                Service.class,
                New.class
        },
        description = "This is just the starting point of the cli tool",
        mixinStandardHelpOptions = true,
        version = {"1.0.1"}
)
public class TopCommand implements Runnable{
        @Override
        public void run() {
        }
}
