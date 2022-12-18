package generators;

import picocli.CommandLine;
import util.WriteProcess;

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

    StringBuilder textCodeProcess() {
        textCode = new StringBuilder();
        textCode = WriteProcess.AddAnnotations(textCode, "Repository");
        textCode.append("public interface " + repositoryName + " extends CrudRepository<" + repositoryName.replace("Repository", "") + ", Long> {}");
        return textCode;
    }

    /**
     * Generator logic
     */
    @Override
    public void run() {
        createFile(repositoryName);
        writeToFile(textCodeProcess());
    }
}
