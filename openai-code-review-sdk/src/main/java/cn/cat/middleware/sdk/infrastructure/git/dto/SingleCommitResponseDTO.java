package cn.cat.middleware.sdk.infrastructure.git.dto;

public class SingleCommitResponseDTO {
    private String sha;
    private Commit commit;
    private CommitFile[] files;
    private String html_url;

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public Commit getCommit() {
        return commit;
    }

    public void setCommit(Commit commit) {
        this.commit = commit;
    }

    public CommitFile[] getFiles() {
        return files;
    }

    public void setFiles(CommitFile[] files) {
        this.files = files;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public static class Commit {
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class CommitFile {
        private String filename;
        private String raw_url;
        private String patch;

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getRaw_url() {
            return raw_url;
        }

        public void setRaw_url(String raw_url) {
            this.raw_url = raw_url;
        }

        public String getPatch() {
            return patch;
        }

        public void setPatch(String patch) {
            this.patch = patch;
        }
    }
}
