package cn.cat.middleware.sdk.infrastructure.context.provider.impl;

import cn.cat.middleware.sdk.infrastructure.context.model.ExecuteProviderParamContext;
import cn.cat.middleware.sdk.infrastructure.context.model.ProviderSwitchConfig;
import cn.cat.middleware.sdk.infrastructure.context.provider.ContextStrategyProvider;
import cn.cat.middleware.sdk.types.enums.ContextStrategyProviderType;

public class FileTypeContextStrategyProvider implements ContextStrategyProvider {

    @Override
    public ContextStrategyProviderType getType() {
        return ContextStrategyProviderType.FILE_TYPE;
    }

    @Override
    public boolean support(ProviderSwitchConfig status) {
        return Boolean.TRUE.equals(status.get(getType().getValue()));
    }

    @Override
    public String execute(ExecuteProviderParamContext context) {
        Object fileData = context.get("fileName");
        if (fileData != null && !fileData.toString().isEmpty()) {
            return "<文件类型说明>" +
                    "当前评审文件的文件类型为" + getFileExtension(fileData.toString()) + "，评审时请按相应知识点进行评审：" +
                    "</文件类型说明>";
        }
        return "";
    }

    private String getFileExtension(String fileName) {
        if (fileName.isEmpty()) return "";
        int index = fileName.lastIndexOf(".");
        if (index == -1) return "";
        return fileName.substring(index + 1);
    }

}
