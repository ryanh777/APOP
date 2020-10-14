package org.petobesityprevention.app.android;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

public class KeyFactory {
    protected static String makeSurveyKey(JSONObject json) {
        //TODO
        // make 20 character hex string key based on hashed data? + time
        //hash((json.get("name") + )json.get("org") + json.get("device_id"), time)

        // change error handling
        String s = "";
        try {
            s = json.getString("name") + json.getString("org") + json.getString("device_id") + LocalDateTime.now().toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MessageDigest digest = null;
        byte[] hashKey = null;
        try {
            digest = MessageDigest.getInstance("SHA-1");
            hashKey = digest.digest(s.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        StringBuilder result = new StringBuilder();
        for (byte aByte : hashKey) {
            result.append(String.format("%02x", aByte));
        }
        return result.toString();

    }

    protected static String makeUserKey(String deviceID, String org) {
        return "USER_" + org + "_" + deviceID;
    }
}
