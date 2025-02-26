package cn.cat.middleware.sdk.infrastructure.llmmodel.common.text;

public enum ChatMessageTextType {

    SYSTEM(SystemMessageText.class),
    USER(UserMessageText.class),
    AI(AIMessageText.class);

    private final Class<? extends ChatMessageText> messageClass;

    ChatMessageTextType(Class<? extends ChatMessageText> clazz) {
        this.messageClass = clazz;
    }

    public Class<? extends ChatMessageText> getMessageClass() {
        return messageClass;
    }

}
