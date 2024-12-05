package cn.cat.middleware.sdk.types.utils;

public class EnvUtils {

    public static String getEnv(String key) {
        String value = System.getenv(key);
        if (null == value || value.isEmpty()) {
            throw new RuntimeException(key + ":" + "value is null");
        }
        return value;
    }

}
