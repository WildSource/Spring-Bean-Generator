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
public class Repository implements Runnable{
    File repositoryFile;

    StringBuilder textCode;

    @CommandLine.Parameters(
            description = "name of the class generated and of the repository"
    )
    String repositoryName;

    void createFile() {
        try {
            repositoryFile = new File(repositoryName + ".java");
            if (repositoryFile.createNewFile()) {
                System.out.println("File created: " + repositoryFile.getName());
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
            FileWriter writer = new FileWriter(repositoryFile);
            textCode = new StringBuilder();

            textCode.append("public interface " + repositoryName + " extends CrudRepository<" + repositoryName.replace("Repository", "") + ", Long> {}");

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
