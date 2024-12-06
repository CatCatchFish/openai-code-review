package cn.cat.middleware.sdk.infrastructure.message.impl;

import cn.cat.middleware.sdk.infrastructure.message.IMessageStrategy;
import cn.cat.middleware.sdk.infrastructure.message.MessageFactory;
import cn.cat.middleware.sdk.infrastructure.message.feishu.FeiShu;
import cn.cat.middleware.sdk.types.utils.EnvUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class FeiShuMessageStrategy implements IMessageStrategy {
    private static final Logger logger = LoggerFactory.getLogger(FeiShuMessageStrategy.class);

    @Override
    public String name() {
        return MessageFactory.MethodType.FEISHU.getName();
    }

    @Override
    public void sendMessage(String logUrl, Map<String, Map<String, String>> data) {
        try {
            String botWebhook = EnvUtils.getEnv("FEISHU_BOT_WEBHOOK");
            FeiShu feiShu = new FeiShu(botWebhook);
            feiShu.sendTestMessage(logUrl, data);
        } catch (Exception e) {
            logger.error("发送飞书消息失败", e);
        }
    }
}
