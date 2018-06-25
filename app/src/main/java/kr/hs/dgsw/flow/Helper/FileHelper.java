package kr.hs.dgsw.flow.Helper;

import android.content.Context;
import android.os.Environment;
import android.support.v4.content.ContextCompat;

import java.io.File;

public class FileHelper {
    public static String getRootDirPath(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File file = ContextCompat.getExternalFilesDirs(context.getApplicationContext(),
                    null)[0];
            return file.getAbsolutePath();
        } else {
            return context.getApplicationContext().getFilesDir().getAbsolutePath();
        }
    }
}
