package generators;

import picocli.CommandLine;
import util.WriteProcess;

@CommandLine.Command(
        name = "repository",
        description = "generate a spring boot repository",
        mixinStandardHelpOptions = true,
        version = {"1.0.1"}
)
public class Repository extends Generator implements Runnable {

    /**
     * all the generated code is stored inside this stringbuilder and is written to a file at the end
     */
    private StringBuilder textCode;

    @CommandLine.Parameters(
            description = "name of the class generated and of the repository"
    )
    private String repositoryName;

    StringBuilder textCodeProcess() {
        setTextCode(new StringBuilder());
        setTextCode(WriteProcess.AddAnnotations(textCode, "Repository"));
        // probably shouldn't use a get method for setting stuff but it's temporary
        getTextCode().append("public interface " + getRepositoryName() + " extends CrudRepository<" + getRepositoryName().replace("Repository", "") + ", Long> {}");
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

    public StringBuilder getTextCode() {
        return textCode;
    }

    public void setTextCode(StringBuilder textCode) {
        this.textCode = textCode;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }
}
