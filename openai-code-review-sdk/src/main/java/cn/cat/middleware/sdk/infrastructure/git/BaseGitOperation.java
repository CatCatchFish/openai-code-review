package cn.cat.middleware.sdk.infrastructure.git;

public interface BaseGitOperation {

    String diff() throws Exception;

    /**
     * 获取文件列表
     *
     * @return 文件列表
     * @throws Exception 异常
     */

    String writeComment(String comment) throws Exception;

}
