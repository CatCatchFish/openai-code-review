package cn.cat.middleware.sdk.infrastructure.message;

import java.util.Map;

public interface IMessageStrategy {

    String name();

    void sendMessage(String logUrl, Map<String, Map<String, String>> data);
}
