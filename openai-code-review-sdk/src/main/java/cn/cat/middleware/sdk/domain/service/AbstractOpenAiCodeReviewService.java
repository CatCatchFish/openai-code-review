package cn.cat.middleware.sdk.domain.service;

import cn.cat.middleware.sdk.infrastructure.git.BaseGitOperation;
import cn.cat.middleware.sdk.infrastructure.git.impl.GitCommand;
import cn.cat.middleware.sdk.infrastructure.message.IMessageStrategy;
import cn.cat.middleware.sdk.infrastructure.openai.IOpenAI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractOpenAiCodeReviewService implements IOpenAiCodeReviewService {
    private final Logger logger = LoggerFactory.getLogger(AbstractOpenAiCodeReviewService.class);

    protected final GitCommand gitCommand;
    protected final IOpenAI openAI;
    protected final IMessageStrategy messageStrategy;
    protected final BaseGitOperation baseGitOperation;

    public AbstractOpenAiCodeReviewService(GitCommand gitCommand, IOpenAI openAI, IMessageStrategy messageStrategy, BaseGitOperation baseGitOperation) {
        this.gitCommand = gitCommand;
        this.openAI = openAI;
        this.messageStrategy = messageStrategy;
        this.baseGitOperation = baseGitOperation;
    }

    @Override
    public void exec() {
        try {
            // 1. 使用Github RestAPI 获取提交代码
            String diffCode = getDiffCode();
            // 2. 开始评审代码
            String recommend = codeReview(diffCode);
            // 3. 记录评审结果；返回日志地址
            String logUrl = recordCodeReview(recommend);
            // 4. 发送消息通知；日志地址、通知的内容
            pushMessage(logUrl);
        } catch (Exception e) {
            logger.error("openai-code-review error", e);
        }
    }

    protected abstract String getDiffCode() throws Exception;

    protected abstract String codeReview(String diffCode) throws Exception;

    protected abstract String recordCodeReview(String recommend) throws Exception;

    protected abstract void pushMessage(String logUrl);

}
