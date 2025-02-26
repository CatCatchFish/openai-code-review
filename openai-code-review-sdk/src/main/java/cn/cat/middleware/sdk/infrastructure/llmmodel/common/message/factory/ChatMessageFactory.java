package cn.cat.middleware.sdk.infrastructure.llmmodel.common.message.factory;

import cn.cat.middleware.sdk.infrastructure.llmmodel.common.message.AssistantChatMessage;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.message.ChatMessage;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.message.SystemChatMessage;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.message.UserChatMessage;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.text.AIMessageText;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.text.ChatMessageText;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.text.SystemMessageText;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.text.UserMessageText;

public class ChatMessageFactory {

    public static ChatMessage createChatMessage(ChatMessageText text) {
        if (text instanceof UserMessageText) {
            return UserChatMessage.from(text.text());
        } else if (text instanceof SystemMessageText) {
            return SystemChatMessage.from(text.text());
        } else if (text instanceof AIMessageText) {
            return AssistantChatMessage.from(text.text());
        } else throw new IllegalArgumentException("Unsupported ChatMessageText type: " + text.getClass());
    }

}
