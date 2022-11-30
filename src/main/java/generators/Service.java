package generators;

import picocli.CommandLine;
import util.WriteProcess;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@CommandLine.Command(
        name = "service",
        description = "generate a spring boot service",
        mixinStandardHelpOptions = true,
        version = {"1.0.0"}
)
public class Service extends Generator implements Runnable {

    StringBuilder textCode;

    @CommandLine.Parameters(
            description = {"name of the service generated"}
    )
    String serviceName;

    @CommandLine.Parameters(
            description = "name of the repository which the service uses"
    )
    String repositoryName;

    void writeToFile() {
        try {
            FileWriter writer = new FileWriter(file);
            textCode = new StringBuilder();

            textCode.append("@Service");
            textCode = WriteProcess.enter(textCode);
            textCode.append("public class " + serviceName + " {\n");
            textCode = WriteProcess.enter(textCode);
            textCode.append("   private " + serviceName + " " + WriteProcess.ObjectToVariable(repositoryName) + ";\n");
            textCode = WriteProcess.enter(textCode);
            textCode.append("   public " + serviceName + "() {}\n");
            textCode = WriteProcess.enter(textCode);
            textCode = WriteProcess.tab(textCode);
            textCode = WriteProcess.AddAnnotations(textCode, "AutoWired");
            textCode.append("   public " + serviceName + "(" + serviceName + " " + WriteProcess.ObjectToVariable(repositoryName) + ") {\n");
            textCode.append("       this." + WriteProcess.ObjectToVariable(repositoryName) + " = " + WriteProcess.ObjectToVariable(repositoryName) + ";\n");
            textCode.append("   }\n");
            textCode.append("}");

            writer.write(textCode.toString());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        createFile(serviceName);
        writeToFile();
    }
}
