package kr.hs.dgsw.flow.Network;

import android.os.AsyncTask;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import kr.hs.dgsw.flow.Helper.EncryptionHelper;
import kr.hs.dgsw.flow.Interface.IPassValue;
import kr.hs.dgsw.flow.Model.GoOut;
import kr.hs.dgsw.flow.Model.Notice;
import kr.hs.dgsw.flow.Network.Request.RegisterRequest;
import kr.hs.dgsw.flow.Network.Response.*;

/**
 * Created by neutral on 10/04/2018.
 */

public class NetworkManager {

    public static final String SERVER_URL = "http://flow.cafe24app.com/";
    public static final String LOGIN_URL = "auth/signin";
    public static final String REGISTER_URL = "auth/signup";
    public static final String OUT_GO_URL = "out/go";
    public static final String OUT_SLEEP_URL = "out/sleep";
    public static final String NOTICE_URL = "notice/";

    private static String CreateURL(String resource) {
        return SERVER_URL + resource;
    }

    public static void login(IPassValue<ResponseFormat<LoginData>> pass, String email, String pw, String registrationToken) {
        JSONObject jobj = new JSONObject();
        try {
            jobj.put("email", email);
            jobj.put("pw", EncryptionHelper.encrypt(pw));
            jobj.put("registration_token", registrationToken);
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        doRequest(LOGIN_URL, Method.POST, pass, "login", jobj);
        ANRequest req = createRequest(LOGIN_URL, Method.POST, "login", jobj);
        req.getAsParsed(new TypeToken<ResponseFormat<LoginData>>() {}, new ParsedRequestListener<ResponseFormat<LoginData>>() {
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

    public static void register(IPassValue<ResponseFormat<Void>> pass, RegisterRequest request) {

        JSONObject jobj = null;
        try {
            jobj = parseToJson(request);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

//        doRequest(REGISTER_URL, Method.POST, pass, "register", jobj);
        ANRequest req = createRequest(REGISTER_URL, Method.POST, "register", jobj);
        req.getAsParsed(new TypeToken<ResponseFormat<Void>>() {}, new ParsedRequestListener<ResponseFormat<Void>>() {
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

    public static void applyOutGo(IPassValue<ResponseFormat<OutData>> pass, String token, Date start, Date end, String reason) {
        JSONObject jobj = new JSONObject();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            jobj.put("start_time", formatter.format(start));
            jobj.put("end_time", formatter.format(end));
            jobj.put("reason", reason);
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        doRequest(OUT_GO_URL, Method.POST, pass, "outgo", jobj);
        ANRequest req = createRequest(OUT_GO_URL, Method.POST, "outgo", jobj, token);
//        req.getAsParsed(new TypeToken<ResponseFormat<OutData>>() {}, new ParsedRequestListener<ResponseFormat<OutData>>() {
//            @Override
//            public void onResponse(ResponseFormat<OutData> response) {
//                pass.passValue(response);
//            }
//
//            @Override
//            public void onError(ANError anError) {
//                pass.passValue(null);
//            }
//        });

        req.getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                ResponseFormat<OutData> data =  parseToValue(response.toString(), new TypeReference<ResponseFormat<OutData>>() { });
                pass.passValue(data);
            }

            @Override
            public void onError(ANError anError) {
                pass.passValue(null);
            }
        });
    }

    public static void applyOutSleep(IPassValue<ResponseFormat<OutSleepData>> pass, String token, String start, String end, String reason) {
        JSONObject jobj = new JSONObject();
        try {
            jobj.put("start_time", start);
            jobj.put("end_time", end);
            jobj.put("reason", reason);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ANRequest req = createRequest(OUT_SLEEP_URL, Method.POST, "outsleep", jobj, token);

        req.getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                ResponseFormat<OutSleepData> data =  parseToValue(response.toString(), new TypeReference<ResponseFormat<OutSleepData>>() { });
                pass.passValue(data);
            }

            @Override
            public void onError(ANError anError) {
                pass.passValue(null);
            }
        });
    }

    public static void applyOutSleep(IPassValue<ResponseFormat<OutSleepData>> pass, String token, Date start, Date end, String reason) {
        JSONObject jobj = new JSONObject();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            jobj.put("start_time", formatter.format(start));
            jobj.put("end_time", formatter.format(end));
            jobj.put("reason", reason);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ANRequest req = createRequest(OUT_SLEEP_URL, Method.POST, "outsleep", jobj, token);

        req.getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                ResponseFormat<OutSleepData> data =  parseToValue(response.toString(), new TypeReference<ResponseFormat<OutSleepData>>() { });
                pass.passValue(data);
            }

            @Override
            public void onError(ANError anError) {
                pass.passValue(null);
            }
        });
    }

    public static void getAllNotice(IPassValue<ResponseFormat<AllNoticeData>> pass, String token) {
        ANRequest request = createRequest(NOTICE_URL, Method.GET, "allnotice", null,token);
        request.getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                ResponseFormat<AllNoticeData> data =  parseToValue(response.toString(), new TypeReference<ResponseFormat<AllNoticeData>>() { });
                pass.passValue(data);
            }

            @Override
            public void onError(ANError anError) {
                pass.passValue(null);
            }
        });
    }

    public static void getNotice(IPassValue<ResponseFormat<Notice>> pass, String token, int noticeIdx) {
        ANRequest request = createRequest(NOTICE_URL + noticeIdx, Method.GET, "notice", null,token);
        request.getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                ResponseFormat<Notice> data =  parseToValue(response.toString(), new TypeReference<ResponseFormat<Notice>>() { });
                pass.passValue(data);
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

    private static ResponseFormat parseToValue(String json, TypeReference type) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectReader reader = mapper.reader(type);

        try {
            ResponseFormat data = reader.readValue(json);
            return data;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static <T> void doRequest(String RESOURCE_URL, Method method, IPassValue<ResponseFormat<T>> pass, String tag, JSONObject jobj) {
        AndroidNetworking.request(CreateURL(RESOURCE_URL), method.getValue())
                .addJSONObjectBody(jobj)
                .setTag(tag)
                .build()
                .getAsParsed(new TypeToken<ResponseFormat<T>>() {}, new ParsedRequestListener<ResponseFormat<T>>() {
                    @Override
                    public void onResponse(ResponseFormat<T> response) {
                        pass.passValue(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        pass.passValue(null);
                    }
                });
    }

    private static ANRequest createRequest(String RESOURCE_URL, Method method, String tag, JSONObject jobj, String token) {
        return AndroidNetworking.request(CreateURL(RESOURCE_URL), method.getValue())
                .addHeaders("x-access-token", token)
                .addJSONObjectBody(jobj)
                .setTag(tag)
                .build();
    }

    private static ANRequest createRequest(String RESOURCE_URL, Method method, String tag, JSONObject jobj) {
        return AndroidNetworking.request(CreateURL(RESOURCE_URL), method.getValue())
                .addJSONObjectBody(jobj)
                .setTag(tag)
                .build();
    }

    public static void loginAsyncTask(IPassValue<ResponseFormat<LoginData>> pass, String email, String pw) {
        LoginAsyncTask loginAsyncTask = new LoginAsyncTask();
        loginAsyncTask.setPass(pass);
        loginAsyncTask.execute(email, pw);
    }

    private static class LoginAsyncTask extends AsyncTask<String, Void, ResponseFormat<LoginData>> {

        IPassValue<ResponseFormat<LoginData>> pass;

        public void setPass(IPassValue<ResponseFormat<LoginData>> pass) {
            this.pass = pass;
        }

        @Override
        protected ResponseFormat<LoginData> doInBackground(String[] strings) {
            if(pass == null) {
                return null;
            }

            String email = strings[0];
            String pw = strings[1];

            JSONObject jobj = new JSONObject();
            try {
                jobj.put("email", email);
                jobj.put("pw", EncryptionHelper.encrypt(pw));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                byte[] data = jobj.toString().getBytes("UTF-8");

                URL url = new URL(CreateURL(LOGIN_URL));

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                urlConnection.setRequestMethod("POST");

                // 데이터를 서버로 업로드 할려면 (POST 메서드를 사용할려면) setDoOutput(true)로 해줘야한다
                urlConnection.setDoOutput(true);

                // HTTP의 바디의 길이가 정해지지 않았을 때 setChunkedStreamingMode(int)
                // 만약 알면 setFixedLengthStreamingMode(int)를 사용하자
                // 둘다 사용하지 않으면 힙메모리를 잡아먹고 대기 시간을 늘린다나 뭐라나
                // urlConnection.setChunkedStreamingMode(0);
                urlConnection.setFixedLengthStreamingMode(data.length);

                // JSON 데이터를 OutputStream에 써준다. (서버로 보낸다)
                OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
                out.write(data);
                out.close();

                // 200(OK)가 떨어졌을때
                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    // InputStream로 Response Content를 받아준다
                    BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    byte[] buf = new byte[4096];
                    ByteArrayOutputStream bo = new ByteArrayOutputStream();
                    int length;
                    while ((length = in.read(buf)) != -1) {
                        bo.write(buf, 0, length);
                    }

                    // Jackson으로 파싱한다
                    ObjectMapper mapper = new ObjectMapper();
                    ResponseFormat<LoginData> resp = mapper.readValue(bo.toString(), new TypeReference<ResponseFormat<LoginData>>() {});

                    return resp;
                } else {
                    return null;    // 200(OK)가 아닐경우 null 리턴
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ResponseFormat<LoginData> loginDataResponseFormat) {
            super.onPostExecute(loginDataResponseFormat);
            pass.passValue(loginDataResponseFormat);
        }
    }

    private enum Method {
        GET(0),POST(1),PUT(2),DELETE(3),HEAD(4),PATCH(5),OPTIONS(6);

        private int value;

        Method(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
