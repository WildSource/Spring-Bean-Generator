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


}
