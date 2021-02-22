package org.petobesityprevention.app.android.files;

import android.content.Context;
import android.util.Log;

import com.amplifyframework.core.Amplify;

import java.io.File;

public class PhotoSubmitter {

    public static void uploadFile(File photo, String surveyID, int photoID) {

        // Make the file from the survey's id
        String imageKey =  "IMAGE_" + surveyID + "_"  + photoID + ".jpg";

        // Upload!
        Amplify.Storage.uploadFile(
                "Photos/" + imageKey,
                photo,
                result -> {
                    Log.i("APOPapp", "Successfuly uploaded photo " + photoID + ": " + result.getKey());
                    Log.i("APOPapp", "Deleted photo " + photoID + " after upload: " + photo.delete());
                },
                storageFailure -> {
                    Log.e("APOPapp", "Photo " + photoID + " upload failed", storageFailure);
                }
        );
    }
}