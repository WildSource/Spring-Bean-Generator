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
    private StringBuilder textCode;

    @CommandLine.Parameters(
            description = {"name of the service generated"}
    )
    private String serviceName;

    private String repositoryName;

    public Service() {
    }

    public Service(String name) {
        if (name.contains("Service")) {
            this.serviceName = name;
            this.repositoryName = name;
        } else {
            this.serviceName = name + "Service";
            this.repositoryName = name + "Repository";
        }
    }

    private void textCodeProcess() {
        setTextCode(new StringBuilder());

        setTextCode(WriteProcess.AddAnnotations(textCode, "Service"));
        setTextCode(WriteProcess.writeClass(textCode, serviceName));
        setTextCode(WriteProcess.enter(textCode));
        setTextCode(WriteProcess.tab(textCode));
        getTextCode().append("private ").append(repositoryName).append(" ").append(WriteProcess.ObjectToVariable(repositoryName)).append(";\n");
        setTextCode(WriteProcess.enter(textCode));
        setTextCode(WriteProcess.tab(textCode));
        getTextCode().append("public ").append(serviceName).append("() {}\n");
        setTextCode(WriteProcess.enter(textCode));
        setTextCode(WriteProcess.tab(textCode));
        setTextCode(WriteProcess.AddAnnotations(textCode, "AutoWired"));
        setTextCode(WriteProcess.tab(textCode));
        getTextCode().append("public ").append(serviceName).append("(").append(repositoryName).append(" ").append(WriteProcess.ObjectToVariable(repositoryName)).append(") {\n");
        setTextCode(WriteProcess.tab(textCode));
        setTextCode(WriteProcess.tab(textCode));
        getTextCode().append("this.").append(WriteProcess.ObjectToVariable(repositoryName)).append(" = ").append(WriteProcess.ObjectToVariable(repositoryName)).append(";\n");
        setTextCode(WriteProcess.tab(textCode));
        getTextCode().append("}\n");
        getTextCode().append("}");
    }

    public StringBuilder getTextCode() {
        return textCode;
    }

    public void setTextCode(StringBuilder textCode) {
        this.textCode = textCode;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    /**
     * Generator logic
     */
    @Override
    public void run() {
        Service service = new Service(serviceName);
        this.serviceName = service.getServiceName();
        this.repositoryName = service.getRepositoryName();

        createFile(getServiceName());
        textCodeProcess();
        writeToFile(getTextCode());
    }
}
