package dgsw.hs.kr.ahnt.Network;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import dgsw.hs.kr.ahnt.Interface.IPassValue;
import dgsw.hs.kr.ahnt.Model.User;
import dgsw.hs.kr.ahnt.Network.Response.*;

/**
 * Created by neutral on 10/04/2018.
 */

public class NetworkManager {

    public static final String SERVER_URL = "http://flow.cafe24app.com/";
    public static final String LOGIN_URL = "auth/signin";
    public static final String REGISTER_URL = "auth/signup";
    public static final String OUT_GO_URL = "out/go";
    public static final String OUT_SLEEP_URL = "out/sleep";

    private static String CreateURL(String resource) {
        return SERVER_URL + resource;
    }

    public static void login(IPassValue<ResponseFormat<LoginData>> pass, String email, String pw) {
        JSONObject jobj = new JSONObject();
        try {
            jobj.put("email", email);
            jobj.put("pw", encrypt(pw));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(CreateURL(LOGIN_URL))
                .addJSONObjectBody(jobj)
                .setTag("login")
                .build()
                .getAsObject(ResponseFormat.class, new ParsedRequestListener<ResponseFormat<LoginData>>() {
                    @Override
                    public void onResponse(ResponseFormat<LoginData> response) {
                        pass.passValue(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        pass.passValue(null);
                    }
                });
    }

    private static String encrypt(String input) {

        String output = "";
        StringBuffer sb = new StringBuffer();

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if (md == null) {
            return "";
        }

        md.update(input.getBytes());

        byte[] msgb = md.digest();

        for (int i = 0; i < msgb.length; i++) {
            byte temp = msgb[i];
            String str = Integer.toHexString(temp & 0xFF);
            while (str.length() < 2) {
                str = "0" + str;
            }
            str = str.substring(str.length() - 2);
            sb.append(str);
        }
        output = sb.toString();

        return output;
    }

    public static void register(IPassValue<ResponseFormat<Void>> pass, User user) {

        JSONObject jobj = null;
        try {
            jobj = parseToJson(user);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(CreateURL(REGISTER_URL))
                .addJSONObjectBody(jobj)
                .setTag("register")
                .build()
                .getAsObject(ResponseFormat.class, new ParsedRequestListener<ResponseFormat<Void>>() {
                    @Override
                    public void onResponse(ResponseFormat<Void> response) {
                        pass.passValue(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        pass.passValue(null);
                    }
                });
    }

    private static JSONObject parseToJson(Object object) throws JsonProcessingException, JSONException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(object);
        return new JSONObject(json);
    }
}
