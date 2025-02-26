package cn.cat.middleware.sdk;

import cn.cat.middleware.sdk.domain.service.impl.OpenAiCodeReviewService;
import cn.cat.middleware.sdk.infrastructure.git.BaseGitOperation;
import cn.cat.middleware.sdk.infrastructure.git.impl.GitCommand;
import cn.cat.middleware.sdk.infrastructure.git.impl.GitRestAPIOperation;
import cn.cat.middleware.sdk.infrastructure.llmmodel.common.chat.ChatLanguageModel;
import cn.cat.middleware.sdk.infrastructure.llmmodel.zhipu.ZhipuAIModel;
import cn.cat.middleware.sdk.infrastructure.message.IMessageStrategy;
import cn.cat.middleware.sdk.infrastructure.message.MessageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static cn.cat.middleware.sdk.types.utils.EnvUtils.getEnv;

public class OpenAiCodeReview {
    private static final Logger logger = LoggerFactory.getLogger(OpenAiCodeReview.class);

    public static void main(String[] args) {
        GitCommand gitCommand = new GitCommand(
                getEnv("GITHUB_REVIEW_LOG_URI"),
                getEnv("GITHUB_TOKEN"),
                getEnv("COMMIT_PROJECT"),
                getEnv("COMMIT_BRANCH"),
                getEnv("COMMIT_AUTHOR"),
                getEnv("COMMIT_MESSAGE")
        );

        ChatLanguageModel chatLanguageModel = new ZhipuAIModel(getEnv("LLM_APIHOST"), getEnv("LLM_APIKEY"));

        String codeCheckCommitUrl = getEnv("GITHUB_CHECK_COMMIT_URL") + getEnv("COMMIT_BRANCH");
        logger.info("codeCheckCommitUrl: " + codeCheckCommitUrl);
        BaseGitOperation baseGitOperation = new GitRestAPIOperation(codeCheckCommitUrl, getEnv("GITHUB_TOKEN"));

        // 获取当前消息通知的类型
        String notifyType = getEnv("NOTIFY_TYPE");
        IMessageStrategy messageStrategy = MessageFactory.getMessageStrategy(notifyType);

        // 执行代码审查
        OpenAiCodeReviewService openAiCodeReviewService = new OpenAiCodeReviewService(gitCommand, chatLanguageModel, messageStrategy, baseGitOperation);
        openAiCodeReviewService.exec();

        logger.info("openai-code-review done!");
    }

}