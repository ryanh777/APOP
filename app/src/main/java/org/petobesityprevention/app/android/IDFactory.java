package org.petobesityprevention.app.android;

import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.SecretKeyFactory;


public class IDFactory {

    // hashed string of data for naming surveys
    protected static String makeSurveyID(String petName, String org, String device_id, String time) {

        try {
            // get survey data
            String data = petName + org + device_id + time;

            // hash it with sha-1
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] hashKey = digest.digest(data.getBytes(StandardCharsets.UTF_8));

            // build string
            StringBuilder result = new StringBuilder();

            for (byte aByte : hashKey) {
                // make bytes into hex
                result.append(String.format("%02x", aByte));
            }
            return result.toString();
        }
        catch (NoSuchAlgorithmException e) {
            Log.e("APOP App", "NO SUCH HASHING ALGORITHM");
            return "SURVEY_" + org + "_" + device_id + "_" + petName + "_" + time;
        }
    }

    // File key for user database
    protected static String makeUserID(String org, String deviceID, String model) {
        return "USER_" + org + "_" + deviceID + "_" + model;
    }
}
