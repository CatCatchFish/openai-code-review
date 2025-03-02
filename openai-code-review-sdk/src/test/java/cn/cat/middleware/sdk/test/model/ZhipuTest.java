package cn.cat.middleware.sdk.test.model;

import cn.cat.middleware.sdk.infrastructure.llmmodel.common.input.AIInputHelper;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.request.ChatCompletionRequest;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.response.ChatCompletionResponse;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.text.ChatMessageText;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.text.SystemMessageText;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.text.UserMessageText;
import cn.cat.middleware.sdk.infrastructure.llmmodel.zhipu.ZhipiAIHttpClient;
import cn.cat.middleware.sdk.infrastructure.llmmodel.zhipu.ZhipuModelType;
import cn.cat.middleware.sdk.types.utils.BearerTokenUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ZhipuTest {

    private static final Logger logger = LoggerFactory.getLogger(ZhipuTest.class);

    @Test
    public void test() {
        String url = "https://open.bigmodel.cn/api/paas/v4";
        String apiKey = "2da3e6fab628094ec02e201d657413d8.e8vqnK8CNpbuXbQM";

        ZhipiAIHttpClient client = new ZhipiAIHttpClient(url, BearerTokenUtils.getToken(apiKey));

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
        request.setModel(ZhipuModelType.GLM_4_FLASH.getCode());
        request.setMessages(AIInputHelper.toMessages(texts));

        ChatCompletionResponse chatCompletionResponse = client.chatCompletion(request);
        logger.info(chatCompletionResponse.toString());
    }

}
