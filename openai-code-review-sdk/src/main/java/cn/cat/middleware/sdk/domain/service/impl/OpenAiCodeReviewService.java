package cn.cat.middleware.sdk.domain.service.impl;

import cn.cat.middleware.sdk.domain.model.Model;
import cn.cat.middleware.sdk.domain.model.Role;
import cn.cat.middleware.sdk.domain.service.AbstractOpenAiCodeReviewService;
import cn.cat.middleware.sdk.infrastructure.git.BaseGitOperation;
import cn.cat.middleware.sdk.infrastructure.git.impl.GitCommand;
import cn.cat.middleware.sdk.infrastructure.message.IMessageStrategy;
import cn.cat.middleware.sdk.infrastructure.openai.IOpenAI;
import cn.cat.middleware.sdk.infrastructure.openai.dto.ChatCompletionRequestDTO;
import cn.cat.middleware.sdk.infrastructure.openai.dto.ChatCompletionSyncResponseDTO;
import cn.cat.middleware.sdk.infrastructure.message.weixin.dto.TemplateMessageDTO;
import cn.cat.middleware.sdk.infrastructure.openai.prompt.PromptTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static cn.cat.middleware.sdk.types.utils.EnvUtils.getEnv;

public class OpenAiCodeReviewService extends AbstractOpenAiCodeReviewService {
    public OpenAiCodeReviewService(GitCommand gitCommand, IOpenAI openAI, IMessageStrategy messageStrategy, BaseGitOperation baseGitOperation) {
        super(gitCommand, openAI, messageStrategy, baseGitOperation);
    }

    @Override
    protected String getDiffCode() throws Exception {
        return baseGitOperation.diff();
    }

    @Override
    protected String codeReview(String diffCode) throws Exception {
        ChatCompletionRequestDTO chatCompletionRequest = new ChatCompletionRequestDTO();
        chatCompletionRequest.setModel(Model.GLM_4_PLUS.getCode());

        // 提示词
        PromptTemplate promptTemplate = PromptTemplate.from("你是一个{{language}}高级编程架构师，精通各类场景方案和架构设计，请你辅助用户评审代码。");
        Map<String, Object> params = new HashMap<>();
        params.put("language", getEnv("PROJECT_LANGUAGE"));
        ChatCompletionRequestDTO.Prompt prompt = promptTemplate.apply(Role.SYSTEM, params);

        // 用户输入
        String userTemplate = "请你根据 git commit api 记录，对代码做出评审。代码如下:{{diffCode}}";
        PromptTemplate userPromptTemplate = PromptTemplate.from(userTemplate);
        Map<String, Object> userParams = new HashMap<>();
        userParams.put("diffCode", diffCode);
        ChatCompletionRequestDTO.Prompt userPrompt = userPromptTemplate.apply(Role.USER, userParams);

        chatCompletionRequest.setMessages(new ArrayList<ChatCompletionRequestDTO.Prompt>() {
            private static final long serialVersionUID = -7988151926241837899L;

            {
                add(prompt);
                add(userPrompt);
            }
        });

        ChatCompletionSyncResponseDTO completions = openAI.completions(chatCompletionRequest);
        ChatCompletionSyncResponseDTO.Message message = completions.getChoices().get(0).getMessage();
        return message.getContent();
    }

    @Override
    protected String recordCodeReview(String recommend) throws Exception {
        return gitCommand.commitAndPush(recommend);
    }

    @Override
    protected void pushMessage(String logUrl) {
        Map<String, Map<String, String>> data = new HashMap<>();
        TemplateMessageDTO.put(data, TemplateMessageDTO.TemplateKey.REPO_NAME, gitCommand.getProject());
        TemplateMessageDTO.put(data, TemplateMessageDTO.TemplateKey.BRANCH_NAME, gitCommand.getBranch());
        TemplateMessageDTO.put(data, TemplateMessageDTO.TemplateKey.COMMIT_AUTHOR, gitCommand.getAuthor());
        TemplateMessageDTO.put(data, TemplateMessageDTO.TemplateKey.COMMIT_MESSAGE, gitCommand.getMessage());
        messageStrategy.sendMessage(logUrl, data);
    }
}
