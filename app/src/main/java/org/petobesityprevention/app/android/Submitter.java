package org.petobesityprevention.app.android;

import android.content.Context;
import android.util.Log;

import com.amplifyframework.core.Amplify;

import java.io.File;
import java.io.FileWriter;

import org.json.JSONException;
import org.json.JSONObject;

public class Submitter {

    private final Context context;

    protected Submitter(Context c) {
        this.context = c;
    }

    protected JSONObject makeJSON(String petName, String petType, String breed,
                                  String age, String gender, String numDogs,
                                  String numCats, String ownerWeight, String bcss,
                                  String weight, String medical, String comments) {
        JSONObject json = new JSONObject();

        try {
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


    protected void uploadFile(JSONObject json) {
        File exampleFile = new File(context.getFilesDir(), "Example.json");

        try {
            FileWriter writer = new FileWriter(exampleFile);
            writer.write(json.toString());
            writer.close();
        }
        catch (Exception ex) {
            Log.e("APOPapp", "Upload Failed", ex);
        }

        Amplify.Storage.uploadFile(
                "Example.json",
                exampleFile,
                result -> Log.i("APOPapp", "Successfuly uploaded: " + result.getKey()),
                storageFailure -> Log.e("APOPapp", "Upload failed", storageFailure)
        );
    }
}

