package kr.hs.dgsw.flow.Helper;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {

    private static Context context;
    private static final String SP_NAME = "PREF";

    public static void setContext(Context context) {
        SharedPreferencesHelper.context = context;
    }

    public static String getPreference(String key) {
        if(context == null) return "";
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        String val = sp.getString(key, "");
        return val;
    }

    public static void setPreference(String key, String value) {
        if(context == null) return;
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString(key, value);
        ed.commit();
    }
}
