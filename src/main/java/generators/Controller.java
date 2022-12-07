package generators;

import picocli.CommandLine;
import util.WriteProcess;

import java.io.FileWriter;
import java.io.IOException;

@CommandLine.Command(
        name = "controller",
        description = "generate a spring boot controller",
        mixinStandardHelpOptions = true,
        version = {"1.0.0"}
)
public class Controller extends Generator implements Runnable {
    /**
     * all the generated code is stored inside this stringbuilder and is written to a file at the end
     */
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


    /**
     * Adds either the @Controller annotation or @RestController depending on the option flags
     *
     * @param textCode
     * @return StringBuilder (code) with a controller annotation
     */
    StringBuilder restControllerProcess(StringBuilder textCode) {
        if (isRestController) {
            textCode = WriteProcess
                    .AddAnnotations(textCode, "RestController");
        } else {
            textCode = WriteProcess
                    .AddAnnotations(textCode, "Controller");
        }
        return textCode;
    }

    /**
     * Adds a @RequestMapping annotations with specified routes only if -m or --mapping flag is on
     *
     * @param textCode
     * @return StringBuilder (code) with a requestmapping annotation
     */
    StringBuilder requestMappingProcess(StringBuilder textCode) {
        if (routes != null) {
            textCode = WriteProcess
                    .AddAnnotations(textCode, "RequestMapping(\"" + routes + "\")\n");
        }
        return textCode;
    }

    /**
     * this process is mandatory to generate a controller. If a service does not exist it will still write the needed code.
     * This generates more than half of the code.
     *
     * @param textCode
     * @return StringBuilder (code) with class definition
     */
    StringBuilder serviceProcess(StringBuilder textCode) {
        textCode = WriteProcess.writeClass(textCode, controllerName);
        textCode = WriteProcess.enter(textCode);
        textCode = WriteProcess.tab(textCode);
        textCode.append("private " + serviceName + " " + WriteProcess.ObjectToVariable(serviceName) + ";\n");
        textCode = WriteProcess.enter(textCode);
        textCode = WriteProcess.tab(textCode);
        textCode = WriteProcess.AddAnnotations(textCode, "AutoWired");
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

    /**
     * If the get flag is on , it generates a get(bean name) method
     *
     * @param textCode
     * @return getMethod
     */
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

    /**
     * If the post flag is on, it generates a post method
     *
     * @param textCode
     * @return postMethod
     */
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

    /**
     * If the put flag is on, it generates a put method
     *
     * @param textCode
     * @return putMethod
     */
    StringBuilder putProcess(StringBuilder textCode) {
        if (hasPutMapping) {
            textCode = WriteProcess.tab(textCode);
            textCode = WriteProcess.AddAnnotations(textCode, "PutMapping");
            textCode = WriteProcess.tab(textCode);
            textCode.append("public  put" + controllerName.replace("Controller", "") + "() {\n");
            textCode = WriteProcess.enter(textCode);
            textCode = WriteProcess.tab(textCode);
            textCode.append("}\n");
            textCode = WriteProcess.enter(textCode);
        }
        return textCode;
    }

    /**
     * If the delete flag is on, it generates a delete method
     *
     * @param textCode
     * @return putMethod
     */
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

    StringBuilder textCodeProcess() {
        textCode = new StringBuilder();
        //StringBuilder is funneled through the methods pipelines
        //concentrating on implementing features before optimizing it

        textCode = restControllerProcess(textCode);

        textCode = requestMappingProcess(textCode);

        textCode = serviceProcess(textCode);

        textCode = mappingProcess(textCode);

        textCode = postProcess(textCode);

        textCode = putProcess(textCode);

        textCode = deleteProcess(textCode);

        textCode.append("}");
        return textCode;
    }

    @Override
    public void run() {
        createFile(controllerName);
        writeToFile(textCodeProcess());
    }

}
