package generators;

import picocli.CommandLine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@CommandLine.Command(
        name = "controller",
        description = "generate a spring boot controller",
        mixinStandardHelpOptions = true
)
public class Controller implements Runnable{
    File controllerFile;
    @CommandLine.Parameters(
            description = "name of the class generated and of the controller"
    )
    String controllerName = "";

    @CommandLine.Option(
            names = {"-r", "--rest"},
            description = "add @RestController annotation"
    )
    boolean isRestController = false;


    @Override
    public void run() {
        try {
            controllerFile = new File(controllerName + ".java");
            if (controllerFile.createNewFile()) {
                System.out.println("File created: " + controllerFile.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred: This file already exist");
            e.printStackTrace();
        }

        try {
            FileWriter writer = new FileWriter(controllerFile);
            StringBuilder stringBuilder = new StringBuilder();

            if(isRestController) {
                stringBuilder.append("@RestController\n");
            }

            stringBuilder.append("public class " + controllerName + " {\n");
            stringBuilder.append("\n");
            stringBuilder.append("}");

            writer.write(stringBuilder.toString());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
