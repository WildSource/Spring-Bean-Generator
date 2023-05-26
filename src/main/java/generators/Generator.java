package generators;

import picocli.CommandLine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class host the createFileMethod basically to avoid writing this method over and over again
 */
public abstract class Generator {
    @CommandLine.ParentCommand
    TopCommand parentCommand;
    File file;

    void createFile(String name) {
        try {
            file = new File("src/main/java" + name + ".java");
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred: This file already exist");
            e.printStackTrace();
        }
    }

    void writeToFile(StringBuilder textCode) {
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(textCode.toString());
            writer.close();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
