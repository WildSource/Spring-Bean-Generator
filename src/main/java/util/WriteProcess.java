package util;

/**
 * Use this class to perform some writing to the textCode.
 * A bunch of shortcut methods defined like a constructorGenerator, method generator, etc...
 */
public final class WriteProcess {

    private WriteProcess() throws Exception {
    }

    public static StringBuilder enter(StringBuilder textCode) {
        return textCode.append("\n");
    }

    /**
     * Turns a String with a capital letter (Object name) to a String without a capital letter (variable name)
     *
     * @param obj
     * @return  variable
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
}
