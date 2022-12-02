package generators;

import picocli.CommandLine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@CommandLine.Command(
        name = "repository",
        description = "generate a spring boot repository",
        mixinStandardHelpOptions = true,
        version = {"1.0.0"}
)
public class Repository extends Generator implements Runnable {

    /**
     * all the generated code is stored inside this stringbuilder and is written to a file at the end
     */
    StringBuilder textCode;

    @CommandLine.Parameters(
            description = "name of the class generated and of the repository"
    )
    String repositoryName;

    void writeToFile() {
        try {
            FileWriter writer = new FileWriter(file);
            textCode = new StringBuilder();

            textCode.append("public interface " + repositoryName + " extends CrudRepository<" + repositoryName.replace("Repository", "") + ", Long> {}");

            writer.write(textCode.toString());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * generator logic
     */
    @Override
    public void run() {

        createFile(repositoryName);
        writeToFile();
    }
}
