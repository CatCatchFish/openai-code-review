package cn.cat.middleware.sdk.infrastructure.git.impl;

import cn.cat.middleware.sdk.infrastructure.git.BaseGitOperation;
import cn.cat.middleware.sdk.infrastructure.git.dto.SingleCommitResponseDTO;
import cn.cat.middleware.sdk.types.utils.DefaultHttpUtils;
import com.alibaba.fastjson2.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class GitRestAPIOperation implements BaseGitOperation {

    private final String githubRepoUrl;
    private final String githubToken;

    public GitRestAPIOperation(String githubRepoUrl, String githubToken) {
        this.githubRepoUrl = githubRepoUrl;
        this.githubToken = githubToken;
    }

    @Override
    public String diff() throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/vnd.github+json");
        headers.put("Authorization", "Bearer " + githubToken);
        headers.put("X-GitHub-Api-Version", "2022-11-28");
        String result = DefaultHttpUtils.executeGetRequest(this.githubRepoUrl, headers);

        SingleCommitResponseDTO singleCommitResponseDTO = JSON.parseObject(result, SingleCommitResponseDTO.class);
        SingleCommitResponseDTO.CommitFile[] files = singleCommitResponseDTO.getFiles();

        StringBuilder content = new StringBuilder();
        for (SingleCommitResponseDTO.CommitFile file : files) {
            content.append("待评审文件名称：").append(file.getFilename()).append("\n");
            content.append("该文件变更代码：").append(file.getPatch()).append("\n");
        }
        return content.toString();
    }

}
