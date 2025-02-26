package cn.cat.middleware.sdk.infrastructure.llmmodel.common.message;

import cn.cat.middleware.sdk.domain.model.Role;

public class UserChatMessage implements ChatMessage {

    private String role = Role.USER.getCode();
    private String content;
    private String name;

    public static UserChatMessage from(String content) {
        UserChatMessage userChatMessage = new UserChatMessage();
        userChatMessage.setContent(content);
        return userChatMessage;
    }

    public static UserChatMessage from(String content, String name) {
        UserChatMessage userChatMessage = new UserChatMessage();
        userChatMessage.setContent(content);
        userChatMessage.setName(name);
        return userChatMessage;
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
