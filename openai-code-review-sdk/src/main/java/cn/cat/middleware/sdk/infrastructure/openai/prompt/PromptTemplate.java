package cn.cat.middleware.sdk.infrastructure.openai.prompt;

import cn.cat.middleware.sdk.domain.model.Role;
import cn.cat.middleware.sdk.infrastructure.openai.dto.ChatCompletionRequestDTO;

import java.util.Map;

public class PromptTemplate {
    private static final PromptTemplateFactory FACTORY = new DefaultPromptTemplateFactory();

    private final String templateString;
    private final TemplateRender templateRender;

    public PromptTemplate(String template) {
        this.templateString = template;
        this.templateRender = FACTORY.create(() -> {
            return template;
        });
    }

    public String template() {
        return templateString;
    }

    /**
     * 对外提供的渲染方法，传入变量，渲染模板
     *
     * @param variables 变量
     * @return 提示词对象
     */
    public ChatCompletionRequestDTO.Prompt apply(Role role, Map<String, Object> variables) {
        return new ChatCompletionRequestDTO.Prompt(role.getCode(), templateRender.render(variables));
    }

    public static PromptTemplate from(String template) {
        return new PromptTemplate(template);
    }
}
