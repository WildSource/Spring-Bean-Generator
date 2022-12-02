package util;

/**
 * Use this class to perform some writing to the textCode.
 * A bunch of shortcut methods defined like a constructorGenerator, method generator, etc...
 */
public final class WriteProcess {

    /**
     * private constructor to make utility class not instantiable.
     *
     * @throws Exception
     */
    private WriteProcess() throws Exception {
    }

    /**
     * Adds a newline to the stringbuilder received from parameter
     *
     * @param textCode
     * @return stringbuilder(code) with a newLine
     */
    public static StringBuilder enter(StringBuilder textCode) {
        return textCode.append("\n");
    }

    /**
     * Turns a String with a capital letter (Object name) to a String without a capital letter (variable name)
     *
     * @param obj
     * @return variable
     */
    public static StringBuilder ObjectToVariable(String obj) {
        return new StringBuilder(obj)
                .deleteCharAt(0)
                .reverse()
                .append(Character
                        .toLowerCase(obj
                                .charAt(0)))
                .reverse();
    }

    /**
     * This methods takes a StringBuilder(name of the bean) and adds a @ to make it an annotation
     *
     * @param textCode
     * @param annotationName
     * @return
     */
    public static StringBuilder AddAnnotations(StringBuilder textCode, String annotationName) {
        return textCode
                .append("@" + annotationName + "\n");
    }

    /**
     * Adds four spaces to the stringbuilder
     * @param textCode
     * @return
     */
    public static StringBuilder tab(StringBuilder textCode) {
        return textCode
                .append("    ");
    }

    /**
     * This class writes the boilerplate java class definition
     *
     * @param textCode  StringBuilder with the generated code itself
     * @param className Name of the class to be generated
     * @return StringBuilder of the generated code
     */
    public static StringBuilder writeClass(StringBuilder textCode, String className) {
        return textCode.append("public class " + className + " {\n");
    }

}
