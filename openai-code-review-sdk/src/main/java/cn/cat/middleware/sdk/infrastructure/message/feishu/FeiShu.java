package cn.cat.middleware.sdk.infrastructure.message.feishu;

import cn.cat.middleware.sdk.infrastructure.message.feishu.dto.BotMessageRequestDTO;
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

    public void sendTestMessage(String redirectUri) throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        BotMessageRequestDTO requestDTO = new BotMessageRequestDTO();
        BotMessageRequestDTO.BotMessageContent content = new BotMessageRequestDTO.BotMessageContent();

        String sb = "您好，您提交的代码已完成审核，请查看审核结果：" +
                redirectUri;
        content.setText(sb);
        requestDTO.setContent(content);

        String result = DefaultHttpUtils.executePostRequest(this.botWebhook, headers, requestDTO);
        logger.info("飞书消息发送结果：{} ", result);
    }
}
