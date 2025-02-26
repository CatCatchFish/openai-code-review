package cn.cat.middleware.sdk.infrastructure.llmmodel.common.chat;

import cn.cat.middleware.sdk.infrastructure.llmmodel.common.output.Response;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.text.AIMessageText;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.text.ChatMessageText;

import java.util.List;

import static java.util.Arrays.asList;

public interface ChatLanguageModel {

    default Response<AIMessageText> generate(ChatMessageText... messages) {
        return generate(asList(messages));
    }

    Response<AIMessageText> generate(List<ChatMessageText> message);

}
