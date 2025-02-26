package cn.cat.middleware.sdk.infrastructure.llmmodel.zhipu;

import cn.cat.middleware.sdk.infrastructure.llmmodel.common.chat.ChatLanguageModel;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.output.Response;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.request.ChatCompletionRequest;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.response.ChatCompletionResponse;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.text.AIMessageText;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.text.ChatMessageText;

import java.util.List;

public class ZhipuAIModel implements ChatLanguageModel {

    private final String model = ZhipuModelType.GLM_4_PLUS.getCode();
    private final ZhipiAIHttpClient client;

    public ZhipuAIModel(String baseUrl, String apiKey) {
        client = new ZhipiAIHttpClient(baseUrl, apiKey);
    }

    @Override
    public Response<AIMessageText> generate(List<ChatMessageText> message) {
        ChatCompletionRequest request = new ChatCompletionRequest();
        request.setModel(model);
        request.setMessages(ZhipuAIHelper.toMessages(message));
        ChatCompletionResponse chatCompletionResponse = client.chatCompletion(request);
        AIMessageText aiMessageText = ZhipuAIHelper.aiMessageTextFrom(chatCompletionResponse);
        return Response.from(aiMessageText);
    }

}
