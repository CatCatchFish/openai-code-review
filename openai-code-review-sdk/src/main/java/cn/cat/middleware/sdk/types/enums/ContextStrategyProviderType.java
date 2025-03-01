package cn.cat.middleware.sdk.types.enums;

public enum ContextStrategyProviderType {

    FILE_CONTENT("file_content", "文件内容"),
    COMMIT_MESSAGE("commit_message", "提交信息"),
    FILE_TYPE("file_type", "文件类型"),
    ;

    private final String value;
    private final String desc;

    ContextStrategyProviderType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

}
