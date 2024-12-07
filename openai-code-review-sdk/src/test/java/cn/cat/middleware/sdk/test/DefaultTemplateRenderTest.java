package cn.cat.middleware.sdk.test;

import cn.cat.middleware.sdk.infrastructure.openai.prompt.DefaultTemplateRender;
import cn.cat.middleware.sdk.infrastructure.openai.prompt.TemplateRender;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class DefaultTemplateRenderTest {

    @Test
    public void testSingleVarReplace() {
        String template = "Hello, {{name}}!";
        TemplateRender render = new DefaultTemplateRender(template);

        Map<String, Object> vars = new HashMap<>();
        vars.put("name", "Tom");
        String prompt = render.render(vars);
        System.out.println(prompt); // output: Hello, Tom!
    }

}
