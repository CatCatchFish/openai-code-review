package cn.cat.middleware.sdk.infrastructure.openai.prompt;

public interface PromptTemplateFactory {

    /**
     * 创建一个提示词模板
     *
     * @param input 输入参数
     * @return 提示词模板
     */
    TemplateRender create(PromptTemplateInput input);

}
