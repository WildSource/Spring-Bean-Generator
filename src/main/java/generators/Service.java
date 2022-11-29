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
public class Service implements Runnable {
    File serviceFile;

    StringBuilder textCode;

    @CommandLine.Parameters(
            description = {"name of the service generated"}
    )
    String serviceName;

    @CommandLine.Parameters(
            description = "name of the repository which the service uses"
    )
    String repositoryName;

    StringBuilder space(StringBuilder textCode) {
        return textCode.append("\n");
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

    void createFile() {
        try {
            serviceFile = new File(serviceName + ".java");
            if (serviceFile.createNewFile()) {
                System.out.println("File created: " + serviceFile.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred: This file already exist");
            e.printStackTrace();
        }
    }

    void writeToFile() {
        try {
            FileWriter writer = new FileWriter(serviceFile);
            textCode = new StringBuilder();

            textCode.append("@Service");
            textCode = WriteProcess.enter(textCode);
            textCode.append("public class " + serviceName + " {\n");
            textCode = WriteProcess.enter(textCode);
            textCode.append("   private " + repositoryName + " " + ObjectToVariable(repositoryName) + ";\n");
            textCode = WriteProcess.enter(textCode);
            textCode.append("   public " + serviceName + "() {}\n");
            textCode = WriteProcess.enter(textCode);
            textCode.append("   @Autowired\n");
            textCode.append("   public " + serviceName + "(" + serviceName + " " + ObjectToVariable(serviceName) + ") {\n");
            textCode.append("       this." + ObjectToVariable(repositoryName) + " = " + ObjectToVariable(repositoryName) + ";\n");
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
        createFile();
        writeToFile();
    }
}
