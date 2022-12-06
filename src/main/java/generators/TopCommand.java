package generators;

import picocli.CommandLine;

@CommandLine.Command(
        name = "sbg",
        subcommands = {
                Controller.class,
                Repository.class,
                Service.class
        }
)
public class TopCommand implements Runnable{
        @Override
        public void run() {
        }
}
