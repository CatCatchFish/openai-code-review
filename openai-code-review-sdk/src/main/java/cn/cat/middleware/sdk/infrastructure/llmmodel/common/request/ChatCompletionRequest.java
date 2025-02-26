package cn.cat.middleware.sdk.infrastructure.llmmodel.common.request;

import cn.cat.middleware.sdk.infrastructure.llmmodel.common.message.ChatMessage;

import java.util.List;

public class ChatCompletionRequest {

    private String model;
    private List<ChatMessage> messages;
    private String requestId;
    private Integer maxTokens;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Integer getMaxTokens() {
        return maxTokens;
    }

    public void setMaxTokens(Integer maxTokens) {
        this.maxTokens = maxTokens;
    }

}
