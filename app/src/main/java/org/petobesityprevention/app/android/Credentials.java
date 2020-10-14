package org.petobesityprevention.app.android;

import android.content.Context;
import android.util.Log;

import com.amplifyframework.core.Amplify;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Credentials {

    private static Credentials credentials;
    private static String org;
    private static String deviceID;

    private Credentials(String o, String d) {
        org = o;
        deviceID = d;
    }

    public static void setCredentials(String o, String d) {
        credentials = new Credentials(o, d);
    }

    public static Credentials getCredentials() {
        return credentials;
    }

    public static String getDeviceID() {
        return deviceID;
    }

    public static String getOrg() {
        return org;
    }

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

    protected static void submitCredentials(Context context, JSONObject userJSON) {
        //TODO
        // send created (registered) credentials
        String userKey = "Users/" + KeyFactory.makeUserKey("1", "vets") + ".json";

        File userFile = new File(context.getFilesDir(), userKey);

        try {
            FileWriter writer = new FileWriter(userFile);
            writer.write(userJSON.toString());
            writer.close();
        }
        catch (Exception ex) {
            Log.e("APOPapp", "Upload Failed", ex);
        }

        Amplify.Storage.uploadFile(
                userKey,
                userFile,
                result -> Log.i("APOPapp", "Successfuly uploaded: " + result.getKey()),
                storageFailure -> Log.e("APOPapp", "Upload failed", storageFailure)
        );
    }

    protected static boolean checkCredentials(Context context, String key) {
        //TODO
        // compare (name, password) with online (get)
        // fix the names in this
        // key is name of file



        Amplify.Storage.downloadFile(
                key,
                new File(context.getFilesDir() + "/credentials.json"),
                result -> Log.i("APOPapp", "Successfully downloaded: " + result.getFile().getName()),
                error -> Log.e("APOPapp",  "Download Failure", error)
        );

        //TODO
        // need to wait for download
        File credentials = new File(context.getFilesDir() + "/credentials.json");
        try {
            FileReader reader = new FileReader(credentials);
            Log.i("APOPapp", "Reading Credentials " + (char)reader.read());
        } catch (FileNotFoundException e) {
            Log.e("APOPapp", "Credentials File not created");
        } catch (IOException e) {
            Log.e("APOPapp", "Credentials Read Error");
        }
        //TODO compare with filereader
        return true;
    }

    protected void makeToken(File file) {
        //TODO
        // write a token to the filesystem that lasts a desired amount of time
    }

}
