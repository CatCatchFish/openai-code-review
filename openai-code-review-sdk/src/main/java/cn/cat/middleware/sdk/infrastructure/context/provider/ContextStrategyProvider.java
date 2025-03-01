package cn.cat.middleware.sdk.infrastructure.context.provider;

import cn.cat.middleware.sdk.infrastructure.context.model.ExecuteProviderParamContext;
import cn.cat.middleware.sdk.infrastructure.context.model.ProviderSwitchConfig;
import cn.cat.middleware.sdk.types.enums.ContextStrategyProviderType;

public interface ContextStrategyProvider {

    ContextStrategyProviderType getType();

    boolean support(ProviderSwitchConfig status);

    String execute(ExecuteProviderParamContext context);

}
