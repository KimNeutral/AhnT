package kr.hs.dgsw.flow.Network.Response;

import com.fasterxml.jackson.annotation.JsonProperty;

import kr.hs.dgsw.flow.Model.SleepOut;

public class OutSleepData {
    private SleepOut sleepOut;

    @JsonProperty("sleep_out")
    public SleepOut getSleepOut() { return sleepOut; }
    @JsonProperty("sleep_out")
    public void setSleepOut(SleepOut value) { this.sleepOut = value; }
}
