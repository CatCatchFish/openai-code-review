package cn.cat.middleware.sdk.infrastructure.message;

import cn.cat.middleware.sdk.infrastructure.message.impl.FeiShuMessageStrategy;
import cn.cat.middleware.sdk.infrastructure.message.impl.WeiXinMessageStrategy;

import java.util.Map;

public class MessageFactory {
    private static final Map<String, IMessageStrategy> registry = new java.util.HashMap<>();

    static {
        registry.put(MethodType.FEISHU.getName(), new FeiShuMessageStrategy());
        registry.put(MethodType.WEIXIN.getName(), new WeiXinMessageStrategy());
    }

    public static IMessageStrategy getMessageStrategy(String name) {
        return registry.get(name);
    }

    public enum MethodType {
        FEISHU("feishu", "飞书"),
        WEIXIN("weixin", "微信");

        MethodType(String name, String method) {
            this.name = name;
            this.method = method;
        }

        private final String name;
        private final String method;

        public String getName() {
            return name;
        }

        public String getMethod() {
            return method;
        }
    }
}
