package cn.cat.middleware.sdk.infrastructure.llmmodel.common.output;

public class Response<T> {

    private final T content;

    public Response(T content) {
        this.content = content;
    }

    public T getContent() {
        return content;
    }

    public static <T> Response<T> from(T content) {
        return new Response<>(content);
    }

}
