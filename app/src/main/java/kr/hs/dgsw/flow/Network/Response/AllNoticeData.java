package kr.hs.dgsw.flow.Network.Response;

import com.fasterxml.jackson.annotation.JsonProperty;

import kr.hs.dgsw.flow.Model.Notice;

public class AllNoticeData {
    private Notice[] list;

    @JsonProperty("list")
    public Notice[] getList() {
        return list;
    }

    @JsonProperty("list")
    public void setList(Notice[] list) {
        this.list = list;
    }
}
