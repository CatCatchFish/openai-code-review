package cn.cat.middleware.sdk.domain.service;

import cn.cat.middleware.sdk.domain.model.ExecuteCodeReviewContext;
import cn.cat.middleware.sdk.infrastructure.git.AbstractGitOperation;
import cn.cat.middleware.sdk.infrastructure.git.impl.GitCommand;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.chat.ChatLanguageModel;
import cn.cat.middleware.sdk.infrastructure.message.IMessageStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractOpenAiCodeReviewService implements IOpenAiCodeReviewService {
    private final Logger logger = LoggerFactory.getLogger(AbstractOpenAiCodeReviewService.class);

    protected final GitCommand gitCommand;
    protected final ChatLanguageModel chatLanguageModel;
    protected final IMessageStrategy messageStrategy;
    protected final AbstractGitOperation baseGitOperation;
    protected final ExecuteCodeReviewContext context = new ExecuteCodeReviewContext();

    public AbstractOpenAiCodeReviewService(GitCommand gitCommand, ChatLanguageModel chatLanguageModel, IMessageStrategy messageStrategy, AbstractGitOperation baseGitOperation) {
        this.gitCommand = gitCommand;
        this.chatLanguageModel = chatLanguageModel;
        this.messageStrategy = messageStrategy;
        this.baseGitOperation = baseGitOperation;
    }

    @Override
    public void exec() {
        try {
            // 1. 使用Github RestAPI 获取提交代码
            getDiffCode();
            // 2. 开始评审代码
            String recommend = codeReview();
            // 3. 记录评审结果；返回日志地址
            String logUrl = recordCodeReview(recommend);
            // 4. 发送消息通知；日志地址、通知的内容
            pushMessage(logUrl);
        } catch (Exception e) {
            logger.error("openai-code-review error", e);
        }
    }

    protected abstract void getDiffCode() throws Exception;

    protected abstract String codeReview() throws Exception;

    protected abstract String recordCodeReview(String recommend) throws Exception;

    protected abstract void pushMessage(String logUrl);

}
