package cn.cat.middleware.sdk.infrastructure.llmmodel.deepseek;

import cn.cat.middleware.sdk.infrastructure.llmmodel.common.chat.ChatLanguageModel;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.input.AIInputHelper;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.output.Response;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.request.ChatCompletionRequest;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.response.ChatCompletionResponse;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.text.AIMessageText;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.text.ChatMessageText;

import java.util.List;

public class DeepSeekAIModel implements ChatLanguageModel {

    private final String model = DeepSeekModelType.DEEPSEEK_R1.getCode();
    private final DeepSeekAIHttpClient client;

    public DeepSeekAIModel(String baseUrl, String apiKey) {
        client = new DeepSeekAIHttpClient(baseUrl, apiKey);
    }

    @Override
    public Response<AIMessageText> generate(List<ChatMessageText> message) {
        ChatCompletionRequest request = new ChatCompletionRequest();
        request.setModel(model);
        request.setMessages(AIInputHelper.toMessages(message));
        ChatCompletionResponse chatCompletionResponse = client.chatCompletion(request);
        AIMessageText aiMessageText = AIInputHelper.aiMessageTextFrom(chatCompletionResponse);
        return Response.from(aiMessageText);
    }

}
