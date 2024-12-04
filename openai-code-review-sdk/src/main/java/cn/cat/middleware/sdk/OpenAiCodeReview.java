package cn.cat.middleware.sdk;

import cn.cat.middleware.sdk.domain.service.impl.OpenAiCodeReviewService;
import cn.cat.middleware.sdk.infrastructure.git.BaseGitOperation;
import cn.cat.middleware.sdk.infrastructure.git.impl.GitCommand;
import cn.cat.middleware.sdk.infrastructure.git.impl.GitRestAPIOperation;
import cn.cat.middleware.sdk.infrastructure.openai.IOpenAI;
import cn.cat.middleware.sdk.infrastructure.openai.impl.ChatGLM;
import cn.cat.middleware.sdk.infrastructure.weixin.WeiXin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        /*
          项目：{{repo_name.DATA}} 分支：{{branch_name.DATA}} 作者：{{commit_author.DATA}} 说明：{{commit_message.DATA}}
         */
        WeiXin weiXin = new WeiXin(
                getEnv("WEIXIN_APPID"),
                getEnv("WEIXIN_SECRET"),
                getEnv("WEIXIN_TOUSER"),
                getEnv("WEIXIN_TEMPLATE_ID")
        );

        IOpenAI openAI = new ChatGLM(getEnv("CHATGLM_APIHOST"), getEnv("CHATGLM_APIKEYSECRET"));

        String codeCheckCommitUrl = getEnv("GITHUB_CHECK_COMMIT_URL") + getEnv("COMMIT_BRANCH");
        logger.info("codeCheckCommitUrl: " + codeCheckCommitUrl);
        BaseGitOperation baseGitOperation = new GitRestAPIOperation(codeCheckCommitUrl, getEnv("GITHUB_TOKEN"));

        OpenAiCodeReviewService openAiCodeReviewService = new OpenAiCodeReviewService(gitCommand, openAI, weiXin, baseGitOperation);
        openAiCodeReviewService.exec();

        logger.info("openai-code-review done!");
    }

    private static String getEnv(String key) {
        String value = System.getenv(key);
        if (null == value || value.isEmpty()) {
            throw new RuntimeException(key + ":" + "value is null");
        }
        return value;
    }

}