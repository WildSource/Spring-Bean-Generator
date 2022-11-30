package generators;

import picocli.CommandLine;
import util.WriteProcess;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@CommandLine.Command(
        name = "controller",
        description = "generate a spring boot controller",
        mixinStandardHelpOptions = true,
        version = {"1.0.0"}
)
public class Controller extends Generator implements Runnable {
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


    StringBuilder restControllerProcess(StringBuilder textCode) {
        if (isRestController) {
            textCode = WriteProcess
                    .AddAnnotations(textCode, "RestController");
        }
        else {
            textCode = WriteProcess
                    .AddAnnotations(textCode,"Controller");
        }
        return textCode;
    }

    StringBuilder requestMappingProcess(StringBuilder textCode) {
        if (routes != null) {
            textCode = WriteProcess
                    .AddAnnotations(textCode, "RequestMapping(\"" + routes + "\")\n");
        }
        return textCode;
    }

    StringBuilder serviceProcess(StringBuilder textCode) {
        textCode.append("public class " + controllerName + " {\n");
        textCode = WriteProcess.enter(textCode);
        textCode = WriteProcess.tab(textCode);
        textCode.append("private " + serviceName + " " + WriteProcess.ObjectToVariable(serviceName) + ";\n");
        textCode = WriteProcess.enter(textCode);
        textCode = WriteProcess.tab(textCode);
        textCode = WriteProcess.AddAnnotations(textCode,"AutoWired");
        textCode = WriteProcess.tab(textCode);
        textCode.append("public " + controllerName + "(" + serviceName + " " + WriteProcess.ObjectToVariable(serviceName) + ") {\n");
        textCode = WriteProcess.tab(textCode);
        textCode = WriteProcess.tab(textCode);
        textCode.append(WriteProcess.ObjectToVariable(serviceName) + ".this = " + WriteProcess.ObjectToVariable(serviceName) + ";\n");
        textCode = WriteProcess.tab(textCode);
        textCode.append("}\n");
        textCode = WriteProcess.enter(textCode);
        return textCode;
    }

    StringBuilder mappingProcess(StringBuilder textCode) {
        if (hasGetMapping) {
            textCode = WriteProcess.tab(textCode);
            textCode = WriteProcess.AddAnnotations(textCode, "GetMapping");
            textCode = WriteProcess.tab(textCode);
            textCode.append("public  get" + controllerName.replace("Controller", "") + "() {\n");
            textCode = WriteProcess.enter(textCode);
            textCode = WriteProcess.tab(textCode);
            textCode.append("}\n");
            textCode = WriteProcess.enter(textCode);
        }
        return textCode;
    }

    StringBuilder postProcess(StringBuilder textCode) {
        if (hasPostMapping) {
            textCode = WriteProcess.tab(textCode);
            textCode = WriteProcess.AddAnnotations(textCode, "PostMapping");
            textCode = WriteProcess.tab(textCode);
            textCode.append("public  post" + controllerName.replace("Controller", "") + "() {\n");
            textCode = WriteProcess.enter(textCode);
            textCode = WriteProcess.tab(textCode);
            textCode.append("}\n");
            textCode = WriteProcess.enter(textCode);
        }
        return textCode;
    }

    StringBuilder putProcess(StringBuilder textCode) {
        if (hasPutMapping) {
            textCode = WriteProcess.tab(textCode);
            textCode = WriteProcess.AddAnnotations(textCode,"PutMapping");
            textCode = WriteProcess.tab(textCode);
            textCode.append("public  put" + controllerName.replace("Controller", "") + "() {\n");
            textCode = WriteProcess.enter(textCode);
            textCode = WriteProcess.tab(textCode);
            textCode.append("}\n");
            textCode = WriteProcess.enter(textCode);
        }
        return textCode;
    }

    StringBuilder deleteProcess(StringBuilder textCode) {
        if (hasDeleteMapping) {
            textCode = WriteProcess.tab(textCode);
            textCode = WriteProcess.AddAnnotations(textCode, "DeleteMapping");
            textCode = WriteProcess.tab(textCode);
            textCode.append("public  delete" + controllerName.replace("Controller", "") + "() {\n");
            textCode = WriteProcess.enter(textCode);
            textCode = WriteProcess.tab(textCode);
            textCode.append("}\n");
            textCode = WriteProcess.enter(textCode);
        }
        return textCode;
    }

    void writeToFile() {
        try {
            FileWriter writer = new FileWriter(file);
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
        createFile(controllerName);
        writeToFile();
    }

}
