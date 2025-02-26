package cn.cat.middleware.sdk.domain.service.impl;

import cn.cat.middleware.sdk.domain.service.AbstractOpenAiCodeReviewService;
import cn.cat.middleware.sdk.infrastructure.git.BaseGitOperation;
import cn.cat.middleware.sdk.infrastructure.git.impl.GitCommand;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.chat.ChatLanguageModel;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.output.Response;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.text.AIMessageText;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.text.SystemMessageText;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.text.UserMessageText;
import cn.cat.middleware.sdk.infrastructure.message.IMessageStrategy;
import cn.cat.middleware.sdk.infrastructure.message.weixin.dto.TemplateMessageDTO;

import java.util.HashMap;
import java.util.Map;

public class OpenAiCodeReviewService extends AbstractOpenAiCodeReviewService {

    public OpenAiCodeReviewService(GitCommand gitCommand, ChatLanguageModel chatLanguageModel, IMessageStrategy messageStrategy, BaseGitOperation baseGitOperation) {
        super(gitCommand, chatLanguageModel, messageStrategy, baseGitOperation);
    }

    @Override
    protected String getDiffCode() throws Exception {
        return baseGitOperation.diff();
    }

    @Override
    protected String codeReview(String diffCode) throws Exception {
        // 提示词
        SystemMessageText systemMessageText = new SystemMessageText(
                "你是一个Java高级编程架构师，精通各类场景方案和架构设计，请你辅助用户评审代码。"
        );

        UserMessageText userMessageText = new UserMessageText(
                "请你根据 git commit api 记录，对代码做出评审。代码如下:" + diffCode
        );

        Response<AIMessageText> generate = chatLanguageModel.generate(systemMessageText, userMessageText);
        return generate.getContent().text();
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
