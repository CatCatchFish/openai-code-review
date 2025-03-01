package cn.cat.middleware.sdk.domain.model;

import java.util.List;

public class ExecuteCodeReviewContext {

    private String diffCode;
    private List<CodeReviewFile> files;

    public String getDiffCode() {
        return diffCode;
    }

    public void setDiffCode(String diffCode) {
        this.diffCode = diffCode;
    }

    public List<CodeReviewFile> getFiles() {
        return files;
    }

    public void setFiles(List<CodeReviewFile> files) {
        this.files = files;
    }

    public static class CodeReviewFile {

        private String fileName;
        private String diff;
        private String fileContent;

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getDiff() {
            return diff;
        }

        public void setDiff(String diff) {
            this.diff = diff;
        }

        public String getFileContent() {
            return fileContent;
        }

        public void setFileContent(String fileContent) {
            this.fileContent = fileContent;
        }

    }

}
