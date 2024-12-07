package cn.cat.middleware.sdk.domain.model;

public enum Role {
    SYSTEM("system", "系统"),
    USER("user", "用户"),
    ASSISTANT("assistant", "助理");

    Role(String code, String name) {
        this.code = code;
        this.name = name;
    }

    private final String code;
    private final String name;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
