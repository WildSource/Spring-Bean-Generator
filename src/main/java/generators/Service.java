package generators;

import picocli.CommandLine;
import util.WriteProcess;

@CommandLine.Command(
        name = "service",
        description = "generate a spring boot service",
        mixinStandardHelpOptions = true,
        version = {"1.0.1"}
)
public class Service extends Generator implements Runnable {

    /**
     * all the generated code is stored inside this stringbuilder and is written to a file at the end
     */
    StringBuilder textCode;

    @CommandLine.Parameters(
            description = {"name of the service generated"}
    )
    String serviceName;

    @CommandLine.Parameters(
            description = "name of the repository which the service uses"
    )
    String repositoryName;

    StringBuilder textCodeProcess() {
        textCode = new StringBuilder();

        textCode = WriteProcess.AddAnnotations(textCode, "Service");
        textCode = WriteProcess.writeClass(textCode, serviceName);
        textCode = WriteProcess.enter(textCode);
        textCode = WriteProcess.tab(textCode);
        textCode.append("private " + repositoryName + " " + WriteProcess.ObjectToVariable(repositoryName) + ";\n");
        textCode = WriteProcess.enter(textCode);
        textCode = WriteProcess.tab(textCode);
        textCode.append("public " + serviceName + "() {}\n");
        textCode = WriteProcess.enter(textCode);
        textCode = WriteProcess.tab(textCode);
        textCode = WriteProcess.AddAnnotations(textCode, "AutoWired");
        textCode = WriteProcess.tab(textCode);
        textCode.append("public " + serviceName + "(" + repositoryName + " " + WriteProcess.ObjectToVariable(repositoryName) + ") {\n");
        textCode = WriteProcess.tab(textCode);
        textCode = WriteProcess.tab(textCode);
        textCode.append("this." + WriteProcess.ObjectToVariable(repositoryName) + " = " + WriteProcess.ObjectToVariable(repositoryName) + ";\n");
        textCode = WriteProcess.tab(textCode);
        textCode.append("}\n");
        textCode.append("}");
        return textCode;
    }

    /**
     * Generator logic
     */
    @Override
    public void run() {
        createFile(serviceName);
        writeToFile(textCodeProcess());
    }
}
