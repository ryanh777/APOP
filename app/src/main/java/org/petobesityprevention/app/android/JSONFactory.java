package org.petobesityprevention.app.android;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONFactory {

    protected static JSONObject makeSurveyJSON(String org, String deviceID, String petName, String petType, String breed,
                                  String age, String gender, String numDogs,
                                  String numCats, String ownerWeight, String bcss,
                                  String weight, String medical, String comments) {
        JSONObject json = new JSONObject();

        try {
            json.put("org", org);
            json.put("device_id", deviceID);
            json.put("name", petName);
            json.put("type", petType);
            json.put("breed", breed);
            json.put("age", age);
            json.put("gender", gender);
            json.put("num_dogs", numDogs);
            json.put("num_cats", numCats);
            json.put("owner_assess", ownerWeight);
            json.put("bcss", bcss);
            json.put("weight", weight);
            json.put("medical", medical);
            json.put("comments", comments);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    public static JSONObject makeCredentialsJSON(String org, String password) {

        JSONObject json = new JSONObject();

        try {
            json.put("org", org);
            json.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }
}
