package cn.cat.middleware.sdk.domain.service.impl;

import cn.cat.middleware.sdk.domain.model.ExecuteCodeReviewContext;
import cn.cat.middleware.sdk.domain.service.AbstractOpenAiCodeReviewService;
import cn.cat.middleware.sdk.infrastructure.context.model.ExecuteProviderParamContext;
import cn.cat.middleware.sdk.infrastructure.context.model.ProviderSwitchConfig;
import cn.cat.middleware.sdk.infrastructure.context.provider.ContextStrategyProvider;
import cn.cat.middleware.sdk.infrastructure.context.provider.factory.ContextStrategyProviderFactory;
import cn.cat.middleware.sdk.infrastructure.git.AbstractGitOperation;
import cn.cat.middleware.sdk.infrastructure.git.impl.GitCommand;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.chat.ChatLanguageModel;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.input.Prompt;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.output.Response;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.prompt.PromptTemplate;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.text.AIMessageText;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.text.SystemMessageText;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.text.UserMessageText;
import cn.cat.middleware.sdk.infrastructure.message.IMessageStrategy;
import cn.cat.middleware.sdk.infrastructure.message.weixin.dto.TemplateMessageDTO;
import cn.cat.middleware.sdk.types.enums.ContextStrategyProviderType;
import cn.cat.middleware.sdk.types.utils.EnvUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OpenAiCodeReviewService extends AbstractOpenAiCodeReviewService {

    public OpenAiCodeReviewService(GitCommand gitCommand, ChatLanguageModel chatLanguageModel, IMessageStrategy messageStrategy, AbstractGitOperation baseGitOperation) {
        super(gitCommand, chatLanguageModel, messageStrategy, baseGitOperation);
    }

    @Override
    protected void getDiffCode() throws Exception {
        context.setDiffCode(baseGitOperation.diff());
        context.setFiles(baseGitOperation.getFiles());
    }

    @Override
    protected String codeReview() throws Exception {
        List<ExecuteCodeReviewContext.CodeReviewFile> files = context.getFiles();
        StringBuilder builder = new StringBuilder();
        for (ExecuteCodeReviewContext.CodeReviewFile file : files) {
            // 编写系统提示词
            PromptTemplate promptTemplate = PromptTemplate.from("你是一个经验丰富的{{language}}开发者，请你对以下代码进行代码评审。");
            Map<String, Object> variables = new HashMap<>();
            variables.put("language", EnvUtils.getEnv("PROJECT_LANGUAGE"));
            Prompt prompt = promptTemplate.apply(variables);
            SystemMessageText systemMessageText = new SystemMessageText(prompt.getText());

            // 拟定开关配置
            ProviderSwitchConfig switchConfig = new ProviderSwitchConfig();
            switchConfig.put(ContextStrategyProviderType.FILE_CONTENT.getValue(), true);
            switchConfig.put(ContextStrategyProviderType.FILE_TYPE.getValue(), true);
            ExecuteProviderParamContext param = new ExecuteProviderParamContext();
            param.put("fileData", file.getFileContent());
            param.put("fileName", file.getFileName());
            Set<String> keys = switchConfig.keySet();
            for (String key : keys) {
                if (switchConfig.get(key)) {
                    ContextStrategyProvider provider = ContextStrategyProviderFactory.getProvider(key);
                    if (provider.support(switchConfig)) {
                        String execute = provider.execute(param);
                        systemMessageText.appendText(execute);
                    }
                }
            }

            UserMessageText userMessageText = buildUserMessageText(file);
            Response<AIMessageText> generate = chatLanguageModel.generate(systemMessageText, userMessageText);
            builder.append("文件名称：").append(file.getFileName())
                    .append("\n")
                    .append("评审结果：").append(generate.getContent().text())
                    .append("\n");
        }
        return builder.toString();
    }

    private UserMessageText buildUserMessageText(ExecuteCodeReviewContext.CodeReviewFile file) {
        // 用户提示词模板的定义
        String userTemplate = "请您根据git diff记录，对代码做出评审。变更代码如下:{{diffCode}}";
        PromptTemplate userPromptTemplate = PromptTemplate.from(userTemplate);
        Map<String, Object> userParams = new HashMap<>();
        userParams.put("diffCode", file.getDiff());
        Prompt userPrompt = userPromptTemplate.apply(userParams);
        // 用户消息
        return new UserMessageText(userPrompt.getText());
    }

    @Override
    protected String recordCodeReview(String recommend) throws Exception {
        // 写入提交记录，供开发人员查看
        baseGitOperation.writeComment(recommend);
        // 写入日志仓库，供管理员查看
        return gitCommand.writeComment(recommend);
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
