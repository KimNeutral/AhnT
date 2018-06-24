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

    @JsonProperty("uploadName")
    public String getUploadName() { return uploadName; }
    @JsonProperty("uploadName")
    public void setUploadName(String value) { this.uploadName = value; }

    @JsonProperty("uploadDir")
    public String getUploadDir() { return uploadDir; }
    @JsonProperty("uploadDir")
    public void setUploadDir(String value) { this.uploadDir = value; }

    @JsonProperty("noticeIdx")
    public long getNoticeIdx() { return noticeIdx; }
    @JsonProperty("noticeIdx")
    public void setNoticeIdx(long value) { this.noticeIdx = value; }
}