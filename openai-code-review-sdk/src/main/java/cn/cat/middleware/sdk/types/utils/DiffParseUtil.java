package cn.cat.middleware.sdk.types.utils;

public class DiffParseUtil {

    public static int parseLastDiffPosition(String fileDiff) {
        String[] lines = fileDiff.split("\n");
        return lines.length - 1;
    }

}
