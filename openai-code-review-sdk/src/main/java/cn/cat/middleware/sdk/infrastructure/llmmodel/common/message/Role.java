package cn.cat.middleware.sdk.infrastructure.llmmodel.common.message;

public enum Role {

    SYSTEM("system", "系统"),
    USER("user", "用户"),
    ASSISTANT("assistant", "助理"),
    FUNCTION("function", "功能"),
    TOOL("tool", "工具");

    private final String code;
    private final String name;

    Role(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
