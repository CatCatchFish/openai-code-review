package cn.cat.middleware.sdk.infrastructure.openai;

import cn.cat.middleware.sdk.infrastructure.openai.dto.ChatCompletionRequestDTO;
import cn.cat.middleware.sdk.infrastructure.openai.dto.ChatCompletionSyncResponseDTO;

public interface IOpenAI {

    ChatCompletionSyncResponseDTO completions(ChatCompletionRequestDTO requestDTO) throws Exception;

}
