package org.petobesityprevention.app.android;

import android.content.Context;
import android.util.Log;

import com.amplifyframework.core.Amplify;

import java.io.File;
import java.io.FileWriter;

import org.json.JSONObject;

public class SurveySubmitter {

    protected static void uploadFile(Context context, JSONObject surveyJSON) {
        String surveyKey = KeyFactory.makeSurveyKey() + ".json";

        File surveyFile = new File(context.getFilesDir(), surveyKey);

        try {
            FileWriter writer = new FileWriter(surveyFile);
            writer.write(surveyJSON.toString());
            writer.close();
        }
        catch (Exception ex) {
            Log.e("APOPapp", "Upload Failed", ex);
        }

        Amplify.Storage.uploadFile(
                surveyKey,
                surveyFile,
                result -> Log.i("APOPapp", "Successfuly uploaded: " + result.getKey()),
                storageFailure -> Log.e("APOPapp", "Upload failed", storageFailure)
        );
    }
}

