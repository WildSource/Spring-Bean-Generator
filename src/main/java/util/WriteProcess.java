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

    /**
     * Writes a void method definition without params and without closing brackets
     * 
     * @return StringBuilder with a method definition
     */
    public static StringBuilder writeVoidMethod(StringBuilder textCode, String methodName, boolean isPublic) {
        textCode = WriteProcess.tab(textCode);
        textCode.append(isPublicMethod(isPublic) + " void " + methodName + "() {");
        return textCode;
    }

    /**
     *  Writes a method that returns something without parameters
     *
     * @return generated StringBuilder with a method definition appended
     */
    public static StringBuilder writeReturnMethod(StringBuilder textCode, String methodName, boolean isPublic, String methodReturnObject) {
        textCode = WriteProcess.tab(textCode);
        textCode.append(isPublicMethod(isPublic) + " " + methodReturnObject + " " + methodName + "() {");
        return textCode;
    }

    /**
     *  Writes a void method definition with params and without closing brackets
     *
     * @param textCode
     * @param methodName
     * @param isPublic
     * @param listOfParam
     * @return
     */
    public static StringBuilder writeVoidMethodWithParam(StringBuilder textCode, String methodName, boolean isPublic, String[] listOfParam) {
        textCode = WriteProcess.tab(textCode);
        textCode.append(isPublicMethod(isPublic) + " void " + methodName + "(" + listOfParamProcess(listOfParam));
        textCode.append(") {");
        return textCode;
    }

    /**
     *  Writes a method that returns something with parameters
     *
     * @return generated StringBuilder with a method definition containing params appended
     */
    public static StringBuilder writeReturnMethodWithParams(StringBuilder textCode, String methodName, boolean isPublic, String methodReturnObject, String[] listOfParam) {
        textCode = WriteProcess.tab(textCode);
        textCode.append(isPublicMethod(isPublic) + " " + methodReturnObject + " " + methodName + "(" + listOfParamProcess(listOfParam));
        textCode.append(") {");
        return textCode;
    }

    /**
     * Turns an array to a formatted string of parameters
     *
     * each strings of the array must be formatted like this "Object objectName"
     * 
     * @param list
     * @return formatted Strings of parameters
     */
    private static String listOfParamProcess(String[] list) {
        StringBuilder buffer = new StringBuilder();
        for (String param : list) {
            buffer.append(param + ", ");
        }
        return buffer.toString();
    }

    /**
     * Returns either a "public" or "private" string depending on the boolean received as param
     * aka method for refactoring not meant to be used
     *
     * @param isPublic
     * @return
     */
    private static String isPublicMethod(boolean isPublic) {
        String methodRange;
        if(isPublic) {
            methodRange = "public";
        }
        else {
            methodRange = "private";
        }
        return methodRange;
    }
}