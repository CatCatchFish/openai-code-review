package cn.cat.middleware.sdk.infrastructure.llmmodel.common.text;

public class AIMessageText implements ChatMessageText {

    private final String text;

    public AIMessageText(String text) {
        this.text = text;
    }

    @Override
    public ChatMessageTextType type() {
        return ChatMessageTextType.AI;
    }

    @Override
    public String text() {
        return text;
    }

}

