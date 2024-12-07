package cn.cat.middleware.sdk.infrastructure.openai.prompt;

public interface PromptTemplateInput {

    /**
     * 获取提示词模板
     *
     * @return 提示词模板
     */
    String getTemplate();

    /**
     * 获取提示词模板名称
     *
     * @return 提示词模板名称
     */
    default String getName() {
        return "template";
    }
}
