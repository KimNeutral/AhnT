package kr.hs.dgsw.flow.Network.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Date;

import kr.hs.dgsw.flow.Model.GoOut;

public class OutData {
    private GoOut goOut;

    @JsonProperty("go_out")
    public GoOut getGoOut() { return goOut; }
    @JsonProperty("go_out")
    public void setGoOut(GoOut value) { this.goOut = value; }
}
