package cn.cat.middleware.sdk.types.enums;

public enum CommitInfo {
    REPO_NAME("repo_name", "项目名称"),
    BRANCH_NAME("branch_name", "分支名称"),
    COMMIT_AUTHOR("commit_author", "提交者"),
    COMMIT_MESSAGE("commit_message", "提交信息"),
    ;

    private final String code;
    private final String desc;

    CommitInfo(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
