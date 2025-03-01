package cn.cat.middleware.sdk.infrastructure.git;

import cn.cat.middleware.sdk.domain.model.ExecuteCodeReviewContext;

import java.util.List;

public abstract class AbstractGitOperation implements BaseGitOperation {

    public abstract List<ExecuteCodeReviewContext.CodeReviewFile> getFiles() throws Exception;

}
