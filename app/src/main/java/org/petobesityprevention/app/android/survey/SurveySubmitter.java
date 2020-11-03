package org.petobesityprevention.app.android.survey;

import android.content.Context;
import android.util.Log;

import com.amplifyframework.core.Amplify;

import java.io.File;
import java.io.FileWriter;

public class SurveySubmitter {

    public static void uploadFile(Context context, SurveyJSON surveyJSON) {

        // Make the file from the survey's id
        String surveyKey =  surveyJSON.getID() + ".json";
        File surveyFile = new File(context.getFilesDir(), surveyKey);

        try {
            // Write the file
            FileWriter writer = new FileWriter(surveyFile);
            writer.write(surveyJSON.toString());
            writer.close();
        }
        catch (Exception ex) {
            Log.e("APOPapp", "Survey file write failed", ex);
        }

        // Upload!
        Amplify.Storage.uploadFile(
                "Surveys/" + surveyKey,
                surveyFile,
                result -> Log.i("APOPapp", "Successfuly uploaded: " + result.getKey()),
                storageFailure -> Log.e("APOPapp", "Upload failed", storageFailure)
        );

        //TODO
        // Delete the file or make it cached
    }
}