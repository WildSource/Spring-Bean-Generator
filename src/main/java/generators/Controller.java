package generators;

import picocli.CommandLine;

import javax.print.DocFlavor;
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

    StringBuilder textCode;
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

    @CommandLine.Option(
            names = {"-g", "--get"},
            description = "adds the @GetMapping annotations with an empty method"
    )
    boolean hasGetMapping;

    @CommandLine.Option(
            names = {"--post"},
            description = "adds @PostMapping annotations with an empty method"
    )
    boolean hasPostMapping;

    @CommandLine.Option(
            names = {"--put"},
            description = "adds @PutMapping annotations with an empty method"
    )
    boolean hasPutMapping;

    @CommandLine.Option(
            names = {"-d", "--delete"},
            description = "adds @DeleteMapping annotations with an empty method"
    )
    boolean hasDeleteMapping;

    void createFile() {
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
    }

    StringBuilder space(StringBuilder textCode) {
        return textCode.append("\n");
    }

    StringBuilder restControllerProcess(StringBuilder textCode) {
        if(isRestController) {
            textCode.append("@RestController\n");
        }
        return textCode;
    }

    StringBuilder requestMappingProcess(StringBuilder textCode) {
        if(routes != null) {
            textCode.append("@RequestMapping(\"" + routes + "\")\n");
        }
        return textCode;
    }

    StringBuilder ObjectToVariable(String obj) {
        return new StringBuilder(obj)
                .deleteCharAt(0)
                .reverse()
                .append(Character
                        .toLowerCase(obj
                                .charAt(0)))
                .reverse();
    }

    StringBuilder serviceProcess(StringBuilder textCode) {
        textCode.append("public class " + controllerName + " {\n");
        textCode = space(textCode);
        textCode.append("   private " + serviceName + " " + ObjectToVariable(serviceName) + ";\n");
        textCode = space(textCode);
        textCode.append("   @AutoWired\n");
        textCode.append("   public " + controllerName + "(" + serviceName + " " + ObjectToVariable(serviceName) + ") {\n");
        textCode.append("       " + ObjectToVariable(serviceName)+ ".this = " + ObjectToVariable(serviceName) + ";\n");
        textCode.append("   }\n");
        textCode = space(textCode);
        return textCode;
    }

    StringBuilder mappingProcess(StringBuilder textCode) {
        if(hasGetMapping) {
            textCode.append("   @GetMapping\n");
            textCode.append("   public  get" + controllerName.replace("Controller", "") + "() {\n");
            textCode = space(textCode);
            textCode.append("   }\n");
            textCode = space(textCode);
        }
        return textCode;
    }

    StringBuilder postProcess(StringBuilder textCode) {
        if(hasPostMapping) {
            textCode.append("   @PostMapping\n");
            textCode.append("   public  post" + controllerName.replace("Controller", "") + "() {\n");
            textCode = space(textCode);
            textCode.append("   }\n");
            textCode = space(textCode);
        }
        return textCode;
    }

    StringBuilder putProcess(StringBuilder textCode) {
        if(hasPutMapping) {
            textCode.append("   @PutMapping\n");
            textCode.append("   public  put" + controllerName.replace("Controller", "") + "() {\n");
            textCode = space(textCode);
            textCode.append("   }\n");
            textCode = space(textCode);
        }
        return textCode;
    }

    StringBuilder deleteProcess(StringBuilder textCode) {
        if(hasDeleteMapping) {
            textCode.append("   @DeleteMapping\n");
            textCode.append("   public  delete" + controllerName.replace("Controller", "") + "() {\n");
            textCode = space(textCode);
            textCode.append("   }\n");
            textCode = space(textCode);
        }
        return textCode;
    }

    void writeToFile() {
        try {
            FileWriter writer = new FileWriter(controllerFile);
            textCode = new StringBuilder();

            textCode = restControllerProcess(textCode);

            textCode = requestMappingProcess(textCode);

            textCode = serviceProcess(textCode);

            textCode = mappingProcess(textCode);

            textCode = postProcess(textCode);

            textCode = putProcess(textCode);

            textCode = deleteProcess(textCode);

            textCode.append("}");

            writer.write(textCode.toString());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        createFile();
        writeToFile();
    }

}
