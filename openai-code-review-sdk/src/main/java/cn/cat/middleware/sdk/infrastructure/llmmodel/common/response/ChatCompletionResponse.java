package cn.cat.middleware.sdk.infrastructure.llmmodel.common.response;

import cn.cat.middleware.sdk.infrastructure.llmmodel.common.message.AssistantChatMessage;

import java.util.List;

public class ChatCompletionResponse {

    private String id;
    private Integer created;
    private String model;
    private List<ChatCompletionChoice> choices;

    public ChatCompletionResponse() {
    }

    public ChatCompletionResponse(String id, Integer created, String model, List<ChatCompletionChoice> choices) {
        this.id = id;
        this.created = created;
        this.model = model;
        this.choices = choices;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<ChatCompletionChoice> getChoices() {
        return choices;
    }

    public void setChoices(List<ChatCompletionChoice> choices) {
        this.choices = choices;
    }

    public static class ChatCompletionChoice {
        private Integer index;
        private AssistantChatMessage message;
        private String finishReason;

        public ChatCompletionChoice() {
        }

        public ChatCompletionChoice(Integer index, AssistantChatMessage message, String finishReason) {
            this.index = index;
            this.message = message;
            this.finishReason = finishReason;
        }

        public Integer getIndex() {
            return index;
        }

        public void setIndex(Integer index) {
            this.index = index;
        }

        public AssistantChatMessage getMessage() {
            return message;
        }

        public void setMessage(AssistantChatMessage message) {
            this.message = message;
        }

        public String getFinishReason() {
            return finishReason;
        }

        public void setFinishReason(String finishReason) {
            this.finishReason = finishReason;
        }
    }

}
