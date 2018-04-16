package dgsw.hs.kr.ahnt.Network.Request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * Created by neutral on 16/04/2018.
 */

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RegisterRequest {
    private String email;

    private String pw;

    private String name;

    private String gender;

    private String auth;

    private String mobile;

    private String classIdx;

    private String classNumber;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getClassIdx() {
        return classIdx;
    }

    public void setClassIdx(String classIdx) {
        this.classIdx = classIdx;
    }

    public String getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }
}
