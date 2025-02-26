package cn.cat.middleware.sdk.infrastructure.llmmodel.common.text;

public interface ChatMessageText {

    /**
     * 标识消息类型
     *
     * @return 消息类型
     */
    ChatMessageTextType type();

    /**
     * 文本消息内容
     *
     * @return 消息内容
     */
    String text();

}
