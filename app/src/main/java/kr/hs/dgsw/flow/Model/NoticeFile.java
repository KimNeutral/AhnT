package kr.hs.dgsw.flow.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NoticeFile {
    private long idx;
    private String uploadName;
    private String uploadDir;
    private long noticeIdx;

    @JsonProperty("idx")
    public long getIdx() { return idx; }
    @JsonProperty("idx")
    public void setIdx(long value) { this.idx = value; }

    @JsonProperty("upload_name")
    public String getUploadName() { return uploadName; }
    @JsonProperty("upload_name")
    public void setUploadName(String value) { this.uploadName = value; }

    @JsonProperty("upload_dir")
    public String getUploadDir() { return uploadDir; }
    @JsonProperty("upload_dir")
    public void setUploadDir(String value) { this.uploadDir = value; }

    @JsonProperty("notice_idx")
    public long getNoticeIdx() { return noticeIdx; }
    @JsonProperty("notice_idx")
    public void setNoticeIdx(long value) { this.noticeIdx = value; }
}