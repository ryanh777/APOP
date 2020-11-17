package org.petobesityprevention.app.android.user;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    // JSON object of user information
    public static JSONObject makeUserJSON(String org, String device_id, String model) {

        JSONObject json = new JSONObject();

        try {
            json.put("org", org);
            json.put("device_id", device_id);
            json.put("model", model); // can look into https://github.com/jaredrummler/AndroidDeviceNames for friendlier device names
        } catch (JSONException e) {
            Log.e("APOPapp", "Credentials JSON creation failed", e);
        }

        return json;
    }

    // File key for user database
    public static String makeUserID(String deviceID, String model) {
        return "USER_" + deviceID + "_" + model;
    }
}
