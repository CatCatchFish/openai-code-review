package cn.cat.middleware.sdk.infrastructure.llmmodel.deepseek;

public enum DeepSeekModelType {

    DEEPSEEK_V3("deepseek_v3", "适用于DeepSeek V3版本模型"),
    DEEPSEEK_R1("deepseek-r1-250120", "根据输入的自然语言指令和图像信息完成任务，推荐使用 SSE 或同步调用方式请求接口");

    private final String code;
    private final String info;

    DeepSeekModelType(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

}
