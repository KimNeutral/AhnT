package kr.hs.dgsw.flow.Model;


import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Date;

import io.realm.RealmObject;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OutGo extends RealmObject {
    int accept;
    int idx;
    Date startTime;
    Date endTime;
    String reason;
    String classIdx;
    String studentEmail;

    public int getAccept() {
        return accept;
    }

    public void setAccept(int accept) {
        this.accept = accept;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getClassIdx() {
        return classIdx;
    }

    public void setClassIdx(String classIdx) {
        this.classIdx = classIdx;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }
}