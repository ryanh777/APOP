package org.petobesityprevention.app.android;

import android.util.Log;

import java.security.NoSuchAlgorithmException;

import javax.crypto.SecretKeyFactory;

public class Password {

    //TODO
    // how to do key exchange?
    public static String hashPassword(String password) {
        SecretKeyFactory keyFactory = null;
        try
        {
            keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return password;//keyFactory.generateSecret("");
        }
        catch (NoSuchAlgorithmException e) {
            Log.e("APOP App", "NO SUCH HASHING ALGORITHM");
            return null;
        }
    }

}
