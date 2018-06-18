package kr.hs.dgsw.flow.Network.Response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Date;

import kr.hs.dgsw.flow.Model.OutGo;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OutData {
    private OutGo goOut;

    public OutGo getGoOut() {
        return goOut;
    }

    public void setGoOut(OutGo goOut) {
        this.goOut = goOut;
    }
}
