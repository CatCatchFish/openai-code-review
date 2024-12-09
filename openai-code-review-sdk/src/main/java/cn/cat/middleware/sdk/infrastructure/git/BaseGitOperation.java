package cn.cat.middleware.sdk.infrastructure.git;

public interface BaseGitOperation {

    String diff() throws Exception;

    String writeComment(String comment) throws Exception;

}
