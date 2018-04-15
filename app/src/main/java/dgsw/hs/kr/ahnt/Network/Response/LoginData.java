package dgsw.hs.kr.ahnt.Network.Response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import dgsw.hs.kr.ahnt.Model.User;

/**
 * Created by neutral on 10/04/2018.
 */

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LoginData {
    private String token;

    private User user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ClassPojo [token = " + token + ", user = " + user + "]";
    }
}
