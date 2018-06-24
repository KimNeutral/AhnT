package kr.hs.dgsw.flow.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Notice {
    private long idx;
    private String content;
    private String writer;
    private String writeDate;
    private String modifyDate;
    private NoticeFile[] noticeFiles;

    @JsonProperty("idx")
    public long getIdx() { return idx; }
    @JsonProperty("idx")
    public void setIdx(long value) { this.idx = value; }

    @JsonProperty("content")
    public String getContent() { return content; }
    @JsonProperty("content")
    public void setContent(String value) { this.content = value; }

    @JsonProperty("writer")
    public String getWriter() { return writer; }
    @JsonProperty("writer")
    public void setWriter(String value) { this.writer = value; }

    @JsonProperty("writeDate")
    public String getWriteDate() { return writeDate; }
    @JsonProperty("writeDate")
    public void setWriteDate(String value) { this.writeDate = value; }

    @JsonProperty("modifyDate")
    public String getModifyDate() { return modifyDate; }
    @JsonProperty("modifyDate")
    public void setModifyDate(String value) { this.modifyDate = value; }

    @JsonProperty("NoticeFiles")
    public NoticeFile[] getNoticeFiles() { return noticeFiles; }
    @JsonProperty("NoticeFiles")
    public void setNoticeFiles(NoticeFile[] value) { this.noticeFiles = value; }
}
