package cn.cat.middleware.sdk.infrastructure.context.provider.factory;

import cn.cat.middleware.sdk.infrastructure.context.provider.ContextStrategyProvider;
import cn.cat.middleware.sdk.infrastructure.context.provider.impl.FileContextStrategyProvider;
import cn.cat.middleware.sdk.infrastructure.context.provider.impl.FileTypeContextStrategyProvider;
import cn.cat.middleware.sdk.types.enums.ContextStrategyProviderType;

import java.util.HashMap;
import java.util.Map;

public class ContextStrategyProviderFactory {

    public static Map<String, ContextStrategyProvider> PROVIDER_MAP = new HashMap<>();

    static {
        PROVIDER_MAP.put(ContextStrategyProviderType.FILE_CONTENT.getValue(), new FileContextStrategyProvider());
        PROVIDER_MAP.put(ContextStrategyProviderType.FILE_TYPE.getValue(), new FileTypeContextStrategyProvider());
    }

    public static ContextStrategyProvider getProvider(String type) {
        return PROVIDER_MAP.get(type);
    }

}
