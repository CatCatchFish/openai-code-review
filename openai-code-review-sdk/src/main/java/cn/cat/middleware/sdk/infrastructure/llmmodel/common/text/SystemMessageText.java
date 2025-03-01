package cn.cat.middleware.sdk.infrastructure.llmmodel.common.text;

public class SystemMessageText implements ChatMessageText {

    private String text;

    public SystemMessageText(String text) {
        this.text = text;
    }

    @Override
    public ChatMessageTextType type() {
        return ChatMessageTextType.SYSTEM;
    }

    @Override
    public String text() {
        return text;
    }

    @Override
    public void appendText(String text) {
        this.text += text;
    }

}
