package cn.cat.middleware.sdk.infrastructure.context.provider.impl;

import cn.cat.middleware.sdk.infrastructure.context.model.ExecuteProviderParamContext;
import cn.cat.middleware.sdk.infrastructure.context.model.ProviderSwitchConfig;
import cn.cat.middleware.sdk.infrastructure.context.provider.ContextStrategyProvider;
import cn.cat.middleware.sdk.types.enums.ContextStrategyProviderType;

public class FileContextStrategyProvider implements ContextStrategyProvider {

    @Override
    public ContextStrategyProviderType getType() {
        return ContextStrategyProviderType.FILE_CONTENT;
    }

    @Override
    public boolean support(ProviderSwitchConfig status) {
        return Boolean.TRUE.equals(status.get(getType().getValue()));
    }

    @Override
    public String execute(ExecuteProviderParamContext context) {
        Object fileData = context.get("fileData");
        if (fileData != null && !fileData.toString().isEmpty()) {
            return "<评审文件完整内容上下文>" +
                    "以下为当前评审文件的完整内容，评审时可作为参考：" +
                    fileData +
                    "</评审文件完整内容上下文>";
        }
        return "";
    }

}
