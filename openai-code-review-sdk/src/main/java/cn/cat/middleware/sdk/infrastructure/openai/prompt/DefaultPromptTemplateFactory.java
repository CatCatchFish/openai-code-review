package cn.cat.middleware.sdk.infrastructure.openai.prompt;

public class DefaultPromptTemplateFactory implements PromptTemplateFactory {

    @Override
    public TemplateRender create(PromptTemplateInput input) {
        return new DefaultTemplateRender(input.getTemplate());
    }

}
