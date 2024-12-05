package cn.cat.middleware.sdk.infrastructure.message.impl;

import cn.cat.middleware.sdk.infrastructure.message.IMessageStrategy;
import cn.cat.middleware.sdk.infrastructure.message.MessageFactory;
import cn.cat.middleware.sdk.infrastructure.message.weixin.WeiXin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static cn.cat.middleware.sdk.types.utils.EnvUtils.getEnv;

public class WeiXinMessageStrategy implements IMessageStrategy {
    private static final Logger logger = LoggerFactory.getLogger(WeiXinMessageStrategy.class);

    @Override
    public String name() {
        return MessageFactory.MethodType.WEIXIN.getName();
    }

    @Override
    public void sendMessage(String logUrl, Map<String, Map<String, String>> data) {
        try {
            WeiXin weiXin = new WeiXin(
                    getEnv("WEIXIN_APPID"),
                    getEnv("WEIXIN_SECRET"),
                    getEnv("WEIXIN_TOUSER"),
                    getEnv("WEIXIN_TEMPLATE_ID")
            );

            weiXin.sendTemplateMessage(logUrl, data);
        } catch (Exception e) {
            logger.error("发送微信消息失败", e);
        }
    }
}
