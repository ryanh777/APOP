package org.petobesityprevention.app.android;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;

public class JSONFactory {

    // Make survey JSON object to submit to S3
    protected static SurveyJSON makeSurveyJSON(String org, String deviceID, String petName, String petType,
                                               int age, int weight, String sex, String breed,
                                               int numDogs, int numCats, int ownerWeight, int bcss,
                                               String medical, String comments) {

        // Want to keep time consistent, so we only call this once here
        String time = LocalDateTime.now().toString();

        // make the key (aka filename or id) for the survey
        String key = KeyFactory.makeSurveyKey(petName, org, deviceID, time);

        // Construct
        SurveyJSON json = new SurveyJSON(key);

        // Enter data
        try {
            json.put("survey_id", key); // File name
            json.put("org", org);
            json.put("device_id", deviceID);
            json.put("time", time);
            json.put("pet_name", petName);
            json.put("type", petType);
            json.put("breed", breed);
            json.put("age", age);
            json.put("sex", sex);
            json.put("weight", weight);
            json.put("owner_assess", ownerWeight);
            json.put("bcss", bcss);
            json.put("num_dogs", numDogs);
            json.put("num_cats", numCats);
            json.put("medical", medical);
            json.put("comments", comments);
        } catch (JSONException e) {
            Log.e("APOPapp", "JSON Error making survey", e);
            e.printStackTrace();
        }

        return json;
    }

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

    //TODO
    // Organization username and password
    public static JSONObject makeMasterCredentialsJSON(String org, String password) {

        JSONObject json = new JSONObject();

        try {
            json.put("org", org);
            json.put("password", KeyFactory.hashPassword(password));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }
}
