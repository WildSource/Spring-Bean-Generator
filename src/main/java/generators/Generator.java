package generators;

import java.io.File;
import java.io.IOException;

/**
 * This class host the createFileMethod basically to avoid writing this method over and over again
 */
public abstract class Generator {
    File file;

    void createFile(String name) {
        try {
            file = new File(name + ".java");
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
}
