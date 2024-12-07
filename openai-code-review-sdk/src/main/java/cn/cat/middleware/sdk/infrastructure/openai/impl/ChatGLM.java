package cn.cat.middleware.sdk.infrastructure.openai.impl;

import cn.cat.middleware.sdk.infrastructure.openai.IOpenAI;
import cn.cat.middleware.sdk.infrastructure.openai.dto.ChatCompletionRequestDTO;
import cn.cat.middleware.sdk.infrastructure.openai.dto.ChatCompletionSyncResponseDTO;
import cn.cat.middleware.sdk.types.utils.BearerTokenUtils;
import cn.cat.middleware.sdk.types.utils.DefaultHttpUtils;
import com.alibaba.fastjson2.JSON;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ChatGLM implements IOpenAI {
    private final String apiHost;
    private final String apiKeySecret;

    public ChatGLM(String apiHost, String apiKeySecret) {
        this.apiHost = apiHost;
        this.apiKeySecret = apiKeySecret;
    }


    @Override
    public ChatCompletionSyncResponseDTO completions(ChatCompletionRequestDTO requestDTO) throws Exception {
        String token = BearerTokenUtils.getToken(apiKeySecret);

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);
        headers.put("Content-Type", "application/json");
        headers.put("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        String result = DefaultHttpUtils.executePostRequest(apiHost, headers, requestDTO);

        return JSON.parseObject(result, ChatCompletionSyncResponseDTO.class);
    }
}
