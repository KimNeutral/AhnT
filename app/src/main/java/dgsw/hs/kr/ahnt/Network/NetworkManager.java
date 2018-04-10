package dgsw.hs.kr.ahnt.Network;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import dgsw.hs.kr.ahnt.Interface.IPassValue;
import dgsw.hs.kr.ahnt.Network.Response.LoginResponse;

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

    public static void login(IPassValue<LoginResponse> pass, String email, String pw) {
        JSONObject jobj = new JSONObject();
        try {
            jobj.put("email", email);
            jobj.put("password", pw);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(CreateURL(LOGIN_URL))
                .addJSONObjectBody(jobj)
                .setTag("login")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new GsonBuilder()
                                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                                .create();
                        LoginResponse resp = gson.fromJson(response.toString(), LoginResponse.class);
                        pass.passValue(resp);
                    }

                    @Override
                    public void onError(ANError anError) {
                        pass.passValue(null);
                    }
                });
    }
}
