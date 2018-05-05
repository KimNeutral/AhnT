package dgsw.hs.kr.ahnt.Model;

import io.realm.RealmObject;

/**
 * Created by neutral on 10/04/2018.
 */

public class TokenInfo extends RealmObject {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
