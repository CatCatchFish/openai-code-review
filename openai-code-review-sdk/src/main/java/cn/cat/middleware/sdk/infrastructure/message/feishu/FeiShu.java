package cn.cat.middleware.sdk.infrastructure.message.feishu;

import cn.cat.middleware.sdk.infrastructure.message.feishu.dto.BotMessageRequestDTO;
import cn.cat.middleware.sdk.types.enums.CommitInfo;
import cn.cat.middleware.sdk.types.utils.DefaultHttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class FeiShu {
    private static final Logger logger = LoggerFactory.getLogger(FeiShu.class);

    private final String botWebhook;

    public FeiShu(String botWebhook) {
        this.botWebhook = botWebhook;
    }

    public void sendTestMessage(String redirectUri, Map<String, Map<String, String>> data) throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        BotMessageRequestDTO requestDTO = new BotMessageRequestDTO();
        BotMessageRequestDTO.BotMessageContent content = new BotMessageRequestDTO.BotMessageContent();

        String repoName = get(data, CommitInfo.REPO_NAME);
        String branchName = get(data, CommitInfo.BRANCH_NAME);
        String commitAuthor = get(data, CommitInfo.COMMIT_AUTHOR);
        String commitMessage = get(data, CommitInfo.COMMIT_MESSAGE);

        String line = System.getProperty("line.separator");
        String info = "提交仓库：" + repoName + line
                + "分支：" + branchName + line
                + "提交人：" + commitAuthor + line
                + "提交信息：" + commitMessage + line
                + "详情请访问：" + redirectUri;
        content.setText(info);
        requestDTO.setContent(content);

        String result = DefaultHttpUtils.executePostRequest(this.botWebhook, headers, requestDTO);
        logger.info("飞书消息发送结果：{} ", result);
    }

    private static String get(Map<String, Map<String, String>> data, CommitInfo commitInfo) {
        return data.get(commitInfo.getCode()).get("value");
    }
}
