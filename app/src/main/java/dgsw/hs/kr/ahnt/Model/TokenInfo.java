package dgsw.hs.kr.ahnt.Model;

import io.realm.RealmObject;

/**
 * Created by neutral on 10/04/2018.
 */

public class TokenInfo extends RealmObject {
    public String token;

    public TokenInfo(String token) {
        this.token = token;
    }
}
