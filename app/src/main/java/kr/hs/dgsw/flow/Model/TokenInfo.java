package kr.hs.dgsw.flow.Model;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by neutral on 10/04/2018.
 */

public class TokenInfo extends RealmObject {
    private String token;
    private Date createdAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
