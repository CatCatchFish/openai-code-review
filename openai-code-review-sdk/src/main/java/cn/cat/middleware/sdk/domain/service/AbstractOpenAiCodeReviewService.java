package cn.cat.middleware.sdk.domain.service;

import cn.cat.middleware.sdk.infrastructure.git.BaseGitOperation;
import cn.cat.middleware.sdk.infrastructure.git.impl.GitCommand;
import cn.cat.middleware.sdk.infrastructure.openai.IOpenAI;
import cn.cat.middleware.sdk.infrastructure.weixin.WeiXin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public abstract class AbstractOpenAiCodeReviewService implements IOpenAiCodeReviewService {
    private final Logger logger = LoggerFactory.getLogger(AbstractOpenAiCodeReviewService.class);

    protected final GitCommand gitCommand;
    protected final IOpenAI openAI;
    protected final WeiXin weiXin;
    protected final BaseGitOperation baseGitOperation;

    public AbstractOpenAiCodeReviewService(GitCommand gitCommand, IOpenAI openAI, WeiXin weiXin, BaseGitOperation baseGitOperation) {
        this.gitCommand = gitCommand;
        this.openAI = openAI;
        this.weiXin = weiXin;
        this.baseGitOperation = baseGitOperation;
    }

    @Override
    public void exec() {
        try {
            // 1.1 命令行工具获取提交代码
            String diffCode = getDiffCode();
            // 1.2 GitCommitApi获取提交代码
            String diff = getGitCommitApi();
            commitApiReview(diff);
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

    private void commitApiReview(String diff) {
        try {
            // 1. 对commit-api获取的代码进行评审
            String recommend = codeReview(diff);
            // 2. 记录评审结果；返回日志地址
            String logUrl = recordCodeReview(recommend);
            // 3. 发送消息通知；日志地址、通知的内容
            pushMessage(logUrl);
        } catch (Exception e) {
            logger.error("openai-code-review commitApiReview error", e);
        }
    }

    protected abstract String getDiffCode() throws IOException, InterruptedException;

    protected abstract String getGitCommitApi() throws Exception;

    protected abstract String codeReview(String diffCode) throws Exception;

    protected abstract String recordCodeReview(String recommend) throws Exception;

    protected abstract void pushMessage(String logUrl) throws Exception;

}
