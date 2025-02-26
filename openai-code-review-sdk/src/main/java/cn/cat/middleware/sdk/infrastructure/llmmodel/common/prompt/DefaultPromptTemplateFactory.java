package cn.cat.middleware.sdk.infrastructure.llmmodel.common.prompt;

public class DefaultPromptTemplateFactory implements PromptTemplateFactory {

    @Override
    public TemplateRender create(PromptTemplateInput input) {
        return new DefaultTemplateRender(input.getTemplate());
    }

}
