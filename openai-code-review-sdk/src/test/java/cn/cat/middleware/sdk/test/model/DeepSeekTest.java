package cn.cat.middleware.sdk.test.model;

import cn.cat.middleware.sdk.infrastructure.llmmodel.common.input.AIInputHelper;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.output.Response;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.request.ChatCompletionRequest;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.response.ChatCompletionResponse;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.text.AIMessageText;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.text.ChatMessageText;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.text.SystemMessageText;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.text.UserMessageText;
import cn.cat.middleware.sdk.infrastructure.llmmodel.deepseek.DeepSeekAIHttpClient;
import cn.cat.middleware.sdk.infrastructure.llmmodel.deepseek.DeepSeekAIModel;
import cn.cat.middleware.sdk.infrastructure.llmmodel.deepseek.DeepSeekModelType;
import com.alibaba.fastjson2.JSON;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class DeepSeekTest {

    private static final Logger logger = LoggerFactory.getLogger(DeepSeekTest.class);

    @Test
    public void test_client() {
        String url = "https://ark.cn-beijing.volces.com/api/v3";
        String apiKey = "4e1b90b7-8c3a-4be6-b941-d0ba4e4e12d6";

        List<ChatMessageText> texts = new ArrayList<>();
        SystemMessageText systemMessageText = new SystemMessageText(
                "你是一个善于Java编程的程序员，请帮助用户解决问题。"
        );
        UserMessageText userMessageText = new UserMessageText(
                "请用Java写一个冒泡排序"
        );
        texts.add(systemMessageText);
        texts.add(userMessageText);

        ChatCompletionRequest request = new ChatCompletionRequest();
        request.setModel(DeepSeekModelType.DEEPSEEK_R1.getCode());
        request.setMessages(AIInputHelper.toMessages(texts));

        DeepSeekAIHttpClient client = new DeepSeekAIHttpClient(url, apiKey);

        ChatCompletionResponse chatCompletionResponse = client.chatCompletion(request);
        logger.info(JSON.toJSONString(chatCompletionResponse));
    }

    @Test
    public void test_model() {
        String url = "https://ark.cn-beijing.volces.com/api/v3";
        String apiKey = "4e1b90b7-8c3a-4be6-b941-d0ba4e4e12d6";

        List<ChatMessageText> texts = new ArrayList<>();
        SystemMessageText systemMessageText = new SystemMessageText(
                "你是一个善于Java编程的程序员，请帮助用户解决问题。"
        );
        UserMessageText userMessageText = new UserMessageText(
                "请用Java写一个冒泡排序"
        );
        texts.add(systemMessageText);
        texts.add(userMessageText);

        DeepSeekAIModel model = new DeepSeekAIModel(url, apiKey);
        Response<AIMessageText> response = model.generate(texts);
        logger.info(JSON.toJSONString(response.getContent().text()));
    }

}
