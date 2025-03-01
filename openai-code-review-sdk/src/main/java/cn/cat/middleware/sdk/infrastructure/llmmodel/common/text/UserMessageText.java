package cn.cat.middleware.sdk.infrastructure.llmmodel.common.text;

/**
 * 用户提示词文本
 */
public class UserMessageText implements ChatMessageText {

    private String text;

    public UserMessageText(String text) {
        this.text = text;
    }

    @Override
    public ChatMessageTextType type() {
        return ChatMessageTextType.USER;
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
