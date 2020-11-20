package org.petobesityprevention.app.android.survey;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

public class SurveyJSON extends JSONObject {

    // We store the id key of the survey to avoid computing it multiple times
    private String id;

    private SurveyJSON(String id) {
        super();
        this.id  = id;
    }

    public String getID() {
        return id;
    }

    // Make survey JSON object to submit to S3
    public static SurveyJSON makeSurveyJSON(String org, String deviceID, String petName, String petType,
                                               int age, int weight, String sex, String breed,
                                               int numDogs, int numCats, int ownerWeight, int bcss,
                                               String medical, String comments) {

        // Want to keep time consistent, so we only call this once here
        String time = LocalDateTime.now().toString();

        // make the key (aka filename or id) for the survey
        String id = makeSurveyID(petName, org, deviceID, time);

        // Construct
        SurveyJSON json = new SurveyJSON(id);

        // Enter data
        try {
            json.put("survey_id", id); // File name
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

    // hashed string of data for naming surveys
    public static String makeSurveyID(String petName, String org, String device_id, String time) {

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
}
