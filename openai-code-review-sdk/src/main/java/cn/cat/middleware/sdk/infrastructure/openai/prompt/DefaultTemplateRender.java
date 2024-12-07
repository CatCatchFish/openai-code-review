package cn.cat.middleware.sdk.infrastructure.openai.prompt;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultTemplateRender implements TemplateRender {
    private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\{\\{(.+?)}}");
    private final String template;
    private final Set<String> allVariables;

    public DefaultTemplateRender(String template) {
        if (null == template || template.trim().isEmpty()) {
            throw new IllegalArgumentException("提示词模板不能为空");
        }
        this.template = template;
        this.allVariables = extractVariables(template);
    }

    private static Set<String> extractVariables(String template) {
        Set<String> variables = new HashSet<>();
        Matcher matcher = VARIABLE_PATTERN.matcher(template);
        while (matcher.find()) {
            variables.add(matcher.group(1));
        }
        return variables;
    }

    @Override
    public String render(Map<String, Object> variables) {
        // 检查变量是否都有提供
        ensureVariables(variables);
        String result = template;
        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            result = replaceAll(result, entry.getKey(), entry.getValue());
        }
        return result;
    }

    private void ensureVariables(Map<String, Object> providedVariables) {
        allVariables.forEach(v -> {
            if (!providedVariables.containsKey(v)) {
                throw new IllegalArgumentException("缺少变量：" + v);
            }
        });
    }

    private static String replaceAll(String template, String variable, Object value) {
        if (null == value || value.toString().trim().isEmpty()) {
            throw new IllegalArgumentException("变量值不能为空");
        }
        return template.replace(inDoubleCurlBrackets(variable), value.toString());
    }

    private static String inDoubleCurlBrackets(String variable) {
        return "{{" + variable + "}}";
    }
}
