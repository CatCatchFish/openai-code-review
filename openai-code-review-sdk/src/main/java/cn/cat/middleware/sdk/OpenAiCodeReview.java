package cn.cat.middleware.sdk;

import cn.cat.middleware.sdk.domain.model.ChatCompletionRequest;
import cn.cat.middleware.sdk.domain.model.ChatCompletionSyncResponse;
import cn.cat.middleware.sdk.domain.model.Model;
import cn.cat.middleware.sdk.types.utils.BearerTokenUtils;
import com.alibaba.fastjson2.JSON;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class OpenAiCodeReview {

    public static void main(String[] args) throws Exception {
        System.out.println("测试执行");

        // 1. 代码检出
        ProcessBuilder processBuilder = new ProcessBuilder("git", "diff", "HEAD~1", "HEAD");
        processBuilder.directory(new File("."));

        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;

        StringBuilder diffCode = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            diffCode.append(line);
        }

        int exitCode = process.waitFor();
        System.out.println("Exited with code:" + exitCode);

        // 2. chatglm 代码评审
        String log = codeReview(diffCode.toString());
        System.out.println("code review：" + log);

    }

    private static String codeReview(String diffCode) throws Exception {
        String apiKeySecret = "2da3e6fab628094ec02e201d657413d8.e8vqnK8CNpbuXbQM";
        String token = BearerTokenUtils.getToken(apiKeySecret);

        HttpURLConnection connection = getHttpURLConnection(token);

        ChatCompletionRequest chatCompletionRequest = getChatCompletionRequest(diffCode);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = JSON.toJSONString(chatCompletionRequest).getBytes(StandardCharsets.UTF_8);
            os.write(input);
        }

        int responseCode = connection.getResponseCode();
        System.out.println(responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;

        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        connection.disconnect();

        System.out.println("评审结果：" + content);

        ChatCompletionSyncResponse response = JSON.parseObject(content.toString(), ChatCompletionSyncResponse.class);
        return response.getChoices().get(0).getMessage().getContent();

    }

    private static ChatCompletionRequest getChatCompletionRequest(String diffCode) {
        ChatCompletionRequest chatCompletionRequest = new ChatCompletionRequest();
        chatCompletionRequest.setModel(Model.GLM_4_PLUS.getCode());
        chatCompletionRequest.setMessages(new ArrayList<ChatCompletionRequest.Prompt>() {
            private static final long serialVersionUID = -7988151926241837899L;

            {
                add(new ChatCompletionRequest.Prompt("user", "你是一个高级编程架构师，精通各类场景方案、架构设计和编程语言请，请您根据git diff记录，对代码做出评审。代码如下:"));
                add(new ChatCompletionRequest.Prompt("user", diffCode));
            }
        });
        return chatCompletionRequest;
    }

    private static HttpURLConnection getHttpURLConnection(String token) throws IOException {
        URL url = new URL("https://open.bigmodel.cn/api/paas/v4/chat/completions");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + token);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        connection.setDoOutput(true);
        return connection;
    }
}
