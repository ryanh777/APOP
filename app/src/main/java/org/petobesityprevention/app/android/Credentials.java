package org.petobesityprevention.app.android;

import android.content.Context;
import android.util.Log;

import com.amplifyframework.core.Amplify;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Credentials {

    // Credentials fields
    private static Credentials credentials;
    private static String org;
    private static String deviceID;
    private static String model;

    // Constructor
    private Credentials(String o, String d) {
        org = o;
        deviceID = d;
        model = android.os.Build.MODEL;
    }

    // Setter
    public static void setCredentials(String o, String d) { credentials = new Credentials(o, d); }

    // Getters
    public static Credentials getCredentials() { return credentials; }
    public static String getOrg() { return org; }
    public static String getDeviceID() { return deviceID; }
    public static String getModel() { return model; }


    // Register device upon account login
    protected static void registerDeviceToOrg(Context context) {

        // Make file name from user info
        String userKey = "Users/" + IDFactory.makeUserID(org, deviceID, model) + ".json";

        // Make the file and its JSON content
        File userFile = new File(context.getFilesDir(), userKey);
        JSONObject userJSON = JSONFactory.makeUserJSON(org, deviceID, model);

        try {
            // Write the file
            FileWriter writer = new FileWriter(userFile);
            writer.write(userJSON.toString());
            writer.close();
        }
        catch (Exception ex) {
            Log.e("APOPapp", "Credentials file creation failed", ex);
            return; // Don't try to upload broken file
        }

        // Upload the file
        Amplify.Storage.uploadFile(
                userKey,
                userFile,
                result -> Log.i("APOPapp", "Successfuly uploaded credentials: " + result.getKey()),
                storageFailure -> Log.e("APOPapp", "Credentials upload failed", storageFailure)
        );
    }




    //TODO
    // Compare org Username and Password to login
    protected static boolean checkCredentials(Context context, String key) {

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
            Log.e("APOPapp", "Credentials File not found");
        } catch (IOException e) {
            Log.e("APOPapp", "Credentials I/O Error", e);
        }
        //TODO compare with filereader
        return true;
    }


    //TODO
    // write a token to the filesystem that lasts a desired amount of time
    protected void makeToken(String name, Context context) {

        try {
            FileOutputStream fos = context.openFileOutput(name, Context.MODE_PRIVATE);
        }
        catch (FileNotFoundException f) {
            Log.e("APOP App", "Could not make token file");
        }
    }
}
