package cn.cat.middleware.sdk.infrastructure.llmmodel.common.message;

import cn.cat.middleware.sdk.domain.model.Role;

public class AssistantChatMessage implements ChatMessage {

    private String role = Role.ASSISTANT.getCode();
    private String content;
    private String name;

    public static AssistantChatMessage from(String content) {
        AssistantChatMessage chatMessage = new AssistantChatMessage();
        chatMessage.setContent(content);
        return chatMessage;
    }

    public static AssistantChatMessage from(String content, String name) {
        AssistantChatMessage chatMessage = new AssistantChatMessage();
        chatMessage.setContent(content);
        chatMessage.setName(name);
        return chatMessage;
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
