package kr.hs.dgsw.flow.Helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by neutral on 17/04/2018.
 */

public class EncryptionHelper {
    public static String encrypt(String input) {

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
}
