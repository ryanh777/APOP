package org.petobesityprevention.app.android;

import android.content.Context;
import android.util.Log;

import com.amplifyframework.core.Amplify;

import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;

public class Credentials {

    private static File makeCredentialsFile(Context context, String key) {
        //TODO
        // make json?
        File credFile = new File(context.getFilesDir(), key);

        try {
            FileWriter writer = new FileWriter(credFile);
            writer.write("");
            writer.close();
        }
        catch (Exception ex) {
            Log.e("APOPapp", "Credentials Creation Error", ex);
        }

        return credFile;
    }

    protected static void submitCredentials() {
        //TODO
        // send created (registered) credentials
    }

    protected boolean checkCredentials(Context context, String key) {
        //TODO
        // compare (name, password) with online (get)
        // fix the names in this
        Amplify.Storage.downloadFile(
                key,
                new File(context.getFilesDir() + "/download.txt"),
                result -> Log.i("MyAmplifyApp", "Successfully downloaded: " + result.getFile().getName()),
                error -> Log.e("MyAmplifyApp",  "Download Failure", error)
        );

        return true;
    }

    protected void makeToken(File file) {
        //TODO
        // write a token to the filesystem that lasts a desired amount of time
    }

}
