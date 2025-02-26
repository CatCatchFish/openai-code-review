package cn.cat.middleware.sdk.infrastructure.llmmodel.common.prompt;

import java.util.Map;

public interface TemplateRender {

    /**
     * render the template with variables
     * @param variables the variables to render the template
     * @return the rendered template
     */
    String render(Map<String, Object> variables);

}
