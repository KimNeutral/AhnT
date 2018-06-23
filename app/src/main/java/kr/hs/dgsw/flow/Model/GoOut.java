package kr.hs.dgsw.flow.Model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Date;

import io.realm.RealmObject;

public class GoOut extends RealmObject {
    private long accept;
    private long idx;
    private String startTime;
    private String endTime;
    private String reason;
    private long classIdx;
    private String studentEmail;

    @JsonProperty("accept")
    public long getAccept() { return accept; }
    @JsonProperty("accept")
    public void setAccept(long value) { this.accept = value; }

    @JsonProperty("idx")
    public long getIdx() { return idx; }
    @JsonProperty("idx")
    public void setIdx(long value) { this.idx = value; }

    @JsonProperty("start_time")
    public String getStartTime() { return startTime; }
    @JsonProperty("start_time")
    public void setStartTime(String value) { this.startTime = value; }

    @JsonProperty("end_time")
    public String getEndTime() { return endTime; }
    @JsonProperty("end_time")
    public void setEndTime(String value) { this.endTime = value; }

    @JsonProperty("reason")
    public String getReason() { return reason; }
    @JsonProperty("reason")
    public void setReason(String value) { this.reason = value; }

    @JsonProperty("class_idx")
    public long getClassIdx() { return classIdx; }
    @JsonProperty("class_idx")
    public void setClassIdx(long value) { this.classIdx = value; }

    @JsonProperty("student_email")
    public String getStudentEmail() { return studentEmail; }
    @JsonProperty("student_email")
    public void setStudentEmail(String value) { this.studentEmail = value; }
}