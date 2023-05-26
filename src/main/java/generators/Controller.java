package generators;

import picocli.CommandLine;
import util.WriteProcess;

import javax.naming.ldap.Control;

@CommandLine.Command(
        name = "controller",
        description = "generate a spring boot controller",
        mixinStandardHelpOptions = true,
        version = {"1.0.1"}
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

    public Controller() {}

    public Controller(String name) {
        this.controllerName = name + "Controller";
        this.serviceName = name + "Service";
    }

    /**
     * Adds either the @Controller annotation or @RestController depending on the option flags
     *
     * @param textCode
     * @return StringBuilder (code) with a controller annotation
     */
    StringBuilder restControllerProcess(StringBuilder textCode) {
        if (isRestController) {
            WriteProcess
                    .AddAnnotations(textCode, "RestController");
        } else {
            WriteProcess
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
            WriteProcess
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
        WriteProcess.writeClass(textCode, controllerName);
        WriteProcess.enter(textCode);
        WriteProcess.tab(textCode);
        textCode.append("private ").append(serviceName).append(" ").append(WriteProcess.ObjectToVariable(serviceName)).append(";\n");
        WriteProcess.enter(textCode);
        WriteProcess.tab(textCode);
        WriteProcess.AddAnnotations(textCode, "AutoWired");
        WriteProcess.tab(textCode);
        textCode.append("public ").append(controllerName).append("(").append(serviceName).append(" ").append(WriteProcess.ObjectToVariable(serviceName)).append(") {\n");
        WriteProcess.tab(textCode);
        WriteProcess.tab(textCode);
        textCode.append("this.").append(WriteProcess.ObjectToVariable(serviceName)).append(" = ").append(WriteProcess.ObjectToVariable(serviceName)).append(";\n");
        WriteProcess.tab(textCode);
        textCode.append("}\n");
        WriteProcess.enter(textCode);
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
            WriteProcess.tab(textCode);
            WriteProcess.AddAnnotations(textCode, "GetMapping");
            WriteProcess.tab(textCode);
            textCode.append("public  get").append(controllerName.replace("Controller", "")).append("() {\n");
            WriteProcess.enter(textCode);
            WriteProcess.tab(textCode);
            textCode.append("}\n");
            WriteProcess.enter(textCode);
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
            WriteProcess.tab(textCode);
            WriteProcess.AddAnnotations(textCode, "PostMapping");
            WriteProcess.tab(textCode);
            textCode.append("public  post").append(controllerName.replace("Controller", "")).append("() {\n");
            WriteProcess.enter(textCode);
            WriteProcess.tab(textCode);
            textCode.append("}\n");
            WriteProcess.enter(textCode);
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
            WriteProcess.tab(textCode);
            WriteProcess.AddAnnotations(textCode, "PutMapping");
            WriteProcess.tab(textCode);
            textCode.append("public  put").append(controllerName.replace("Controller", "")).append("() {\n");
            WriteProcess.enter(textCode);
            WriteProcess.tab(textCode);
            textCode.append("}\n");
            WriteProcess.enter(textCode);
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
            WriteProcess.tab(textCode);
            WriteProcess.AddAnnotations(textCode, "DeleteMapping");
            WriteProcess.tab(textCode);
            textCode.append("public  delete").append(controllerName.replace("Controller", "")).append("() {\n");
            WriteProcess.enter(textCode);
            WriteProcess.tab(textCode);
            textCode.append("}\n");
            WriteProcess.enter(textCode);
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
        Controller controller = new Controller(controllerName);
        this.controllerName = controller.getControllerName();
        this.serviceName = controller.getServiceName();

        createFile(controllerName);
        writeToFile(textCodeProcess());
    }

    public StringBuilder getTextCode() {
        return textCode;
    }

    public void setTextCode(StringBuilder textCode) {
        this.textCode = textCode;
    }

    public String getControllerName() {
        return controllerName;
    }

    public void setControllerName(String controllerName) {
        this.controllerName = controllerName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public boolean isRestController() {
        return isRestController;
    }

    public void setRestController(boolean restController) {
        isRestController = restController;
    }

    public String getRoutes() {
        return routes;
    }

    public void setRoutes(String routes) {
        this.routes = routes;
    }

    public boolean isHasGetMapping() {
        return hasGetMapping;
    }

    public void setHasGetMapping(boolean hasGetMapping) {
        this.hasGetMapping = hasGetMapping;
    }

    public boolean isHasPostMapping() {
        return hasPostMapping;
    }

    public void setHasPostMapping(boolean hasPostMapping) {
        this.hasPostMapping = hasPostMapping;
    }

    public boolean isHasPutMapping() {
        return hasPutMapping;
    }

    public void setHasPutMapping(boolean hasPutMapping) {
        this.hasPutMapping = hasPutMapping;
    }

    public boolean isHasDeleteMapping() {
        return hasDeleteMapping;
    }

    public void setHasDeleteMapping(boolean hasDeleteMapping) {
        this.hasDeleteMapping = hasDeleteMapping;
    }
}
