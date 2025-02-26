package cn.cat.middleware.sdk.infrastructure.llmmodel.zhipu;

import cn.cat.middleware.sdk.infrastructure.llmmodel.common.request.ChatCompletionRequest;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.response.ChatCompletionResponse;
import cn.cat.middleware.sdk.types.utils.DefaultHttpUtils;
import com.alibaba.fastjson2.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ZhipiAIHttpClient {

    private static final Logger logger = LoggerFactory.getLogger(ZhipiAIHttpClient.class);

    private final String baseUrl;
    private final String apiKey;

    public ZhipiAIHttpClient(String baseUrl, String apiKey) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
    }

    public ChatCompletionResponse chatCompletion(ChatCompletionRequest request) {
        try {
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + apiKey);
            headers.put("Content-Type", "application/json");
            String url = baseUrl + "/chat/completions";
            logger.info("智谱模型请求应答开始，请求参数: {}", JSON.toJSONString(request));
            String response = DefaultHttpUtils.executePostRequest(url, headers, request);
            ChatCompletionResponse chatCompletionResponse = JSON.parseObject(response, ChatCompletionResponse.class);
            logger.info("智谱模型请求应答结束，应答参数: {}", JSON.toJSONString(chatCompletionResponse));
            return chatCompletionResponse;
        } catch (Exception e) {
            logger.error("chat completion error", e);
        }
        return null;
    }

}
