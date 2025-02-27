package cn.cat.middleware.sdk.infrastructure.llmmodel.common.input;

import cn.cat.middleware.sdk.infrastructure.llmmodel.common.message.AssistantChatMessage;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.message.ChatMessage;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.message.factory.ChatMessageFactory;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.response.ChatCompletionResponse;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.text.AIMessageText;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.text.ChatMessageText;

import java.util.List;
import java.util.stream.Collectors;

public class AIInputHelper {

    public static List<ChatMessage> toMessages(List<ChatMessageText> texts) {
        return texts.stream()
                .map(AIInputHelper::toMessage)
                .collect(Collectors.toList());
    }

    public static ChatMessage toMessage(ChatMessageText text) {
        return ChatMessageFactory.createChatMessage(text);
    }

    public static AIMessageText aiMessageTextFrom(ChatCompletionResponse response) {
        AssistantChatMessage message = response.getChoices().get(0).getMessage();
        return new AIMessageText(message.getContent());
    }

}
