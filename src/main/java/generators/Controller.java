package generators;

import picocli.CommandLine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@CommandLine.Command(
        name = "controller",
        description = "generate a spring boot controller",
        mixinStandardHelpOptions = true,
        version = {"1.0.0"}
)
public class Controller implements Runnable{
    File controllerFile;
    @CommandLine.Parameters(
            description = "name of the class generated and of the controller"
    )
    String controllerName;

    @CommandLine.Parameters(
            description = "name of the service which the controller uses"
    )
    String serviceName;

    @CommandLine.Option(
            names = {"-r", "--rest"},
            description = "add @RestController annotation"
    )
    boolean isRestController;

    @CommandLine.Option(
            names = {"-m", "--mapping"},
            description = "adds a @RequestMapping annotations, if no args are feeded it defauls to \"/\""
    )
    String routes;


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

            if(routes != null) {
                stringBuilder.append("@RequestMapping(\"" + routes + "\")\n");
            }

            stringBuilder.append("public class " + controllerName + " {\n");
            stringBuilder.append("\n");
            stringBuilder.append("private " + serviceName + " " + new StringBuilder(serviceName)
                    .deleteCharAt(0)
                    .reverse()
                    .append(Character
                            .toLowerCase(serviceName
                                    .charAt(0)))
                    .reverse() + ";\n");
            stringBuilder.append("\n");
            stringBuilder.append("}");

            writer.write(stringBuilder.toString());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
