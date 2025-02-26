package cn.cat.middleware.sdk.infrastructure.llmmodel.common.message;

import cn.cat.middleware.sdk.domain.model.Role;

public final class SystemChatMessage implements ChatMessage {

    private String role = Role.SYSTEM.getCode();
    private String content;
    private String name;

    public static SystemChatMessage from(String content) {
        SystemChatMessage systemChatMessage = new SystemChatMessage();
        systemChatMessage.setContent(content);
        return systemChatMessage;
    }

    public static SystemChatMessage from(String content, String name) {
        SystemChatMessage systemChatMessage = new SystemChatMessage();
        systemChatMessage.setContent(content);
        systemChatMessage.setName(name);
        return systemChatMessage;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
