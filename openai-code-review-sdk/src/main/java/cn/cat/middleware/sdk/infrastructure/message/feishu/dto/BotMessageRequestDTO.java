package cn.cat.middleware.sdk.infrastructure.message.feishu.dto;

public class BotMessageRequestDTO {
    private String msg_type = "text";

    private BotMessageContent content;

    public String getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(String msg_type) {
        this.msg_type = msg_type;
    }

    public BotMessageContent getContent() {
        return content;
    }

    public void setContent(BotMessageContent content) {
        this.content = content;
    }

    public static class BotMessageContent {
        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
