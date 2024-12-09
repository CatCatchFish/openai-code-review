package cn.cat.middleware.sdk.infrastructure.git.impl;

import cn.cat.middleware.sdk.infrastructure.git.BaseGitOperation;
import cn.cat.middleware.sdk.infrastructure.git.dto.CommitCommentRequestDTO;
import cn.cat.middleware.sdk.infrastructure.git.dto.SingleCommitResponseDTO;
import cn.cat.middleware.sdk.types.utils.DefaultHttpUtils;
import cn.cat.middleware.sdk.types.utils.DiffParseUtil;
import com.alibaba.fastjson2.JSON;

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
        SingleCommitResponseDTO singleCommitResponseDTO = getCommitResponse();
        SingleCommitResponseDTO.CommitFile[] files = singleCommitResponseDTO.getFiles();

        StringBuilder content = new StringBuilder();
        for (SingleCommitResponseDTO.CommitFile file : files) {
            content.append("待评审文件名称：").append(file.getFilename()).append("\n");
            content.append("该文件变更代码：").append(file.getPatch()).append("\n");
        }
        return content.toString();
    }

    @Override
    public String writeComment(String comment) throws Exception {
        SingleCommitResponseDTO singleCommitResponseDTO = getCommitResponse();
        SingleCommitResponseDTO.CommitFile[] files = singleCommitResponseDTO.getFiles();
        // 由于代码评审时是一次评审多个文件，所以这里只取第一个文件写入评审评论
        SingleCommitResponseDTO.CommitFile file = files[0];

        // 获取变更代码的最后位置
        int diffPositionIndex = DiffParseUtil.parseLastDiffPosition(file.getPatch());

        CommitCommentRequestDTO request = new CommitCommentRequestDTO();
        request.setBody(comment);
        request.setPosition(diffPositionIndex);
        request.setPath(file.getFilename());
        writeComment(request);

        return singleCommitResponseDTO.getHtml_url();
    }

    private void writeComment(CommitCommentRequestDTO request) throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/vnd.github+json");
        headers.put("Authorization", "Bearer " + githubToken);
        headers.put("X-GitHub-Api-Version", "2022-11-28");
        DefaultHttpUtils.executePostRequest(this.githubRepoUrl + "/comments", headers, request);
    }

    private SingleCommitResponseDTO getCommitResponse() throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/vnd.github+json");
        headers.put("Authorization", "Bearer " + githubToken);
        headers.put("X-GitHub-Api-Version", "2022-11-28");
        String result = DefaultHttpUtils.executeGetRequest(this.githubRepoUrl, headers);

        return JSON.parseObject(result, SingleCommitResponseDTO.class);
    }

}
