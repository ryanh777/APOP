package org.petobesityprevention.app.android.user;

import android.content.Context;
import android.util.Log;

import com.amplifyframework.core.Amplify;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

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
        String userKey = "Users/" + User.makeUserID(org, deviceID, model) + ".json";

        // Make the file and its JSON content
        File userFile = new File(context.getFilesDir(), userKey);
        JSONObject userJSON = User.makeUserJSON(org, deviceID, model);

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

        // Store account token
        Token.makeCacheToken(context); //todo implement threading to save mem access time
        Token.makeFileToken(context);
    }


    //TODO
    // Compare org Username and Password to login
    public static boolean checkCredentials(String username, String hashedPassword, Context context) {

        // S3 File will be named this
        String key = username + ".json";

        AtomicBoolean checked = new AtomicBoolean(false);

        Amplify.Storage.downloadFile(
                key,
                new File(context.getFilesDir() + "/credentials.json"),
                result -> {
                    Log.i("APOPapp", "Successfully downloaded: " + result.getFile().getName());

                    checked.set(true); },
                error -> Log.e("APOPapp",  "Download Failure", error)
        );

        //while (!checked.get()) {
            // waiting for download
        //}

        //TODO
        // need to wait for download
        File credentials = new File(context.getFilesDir() + "/credentials.json");
        try {
            FileReader reader = new FileReader(credentials);
            Log.i("APOPapp", "Reading Credentials " + (char)reader.read());
            //TODO compare with filereader
            //TODO delete file
        } catch (FileNotFoundException e) {
            Log.e("APOPapp", "Credentials File not found");
        } catch (IOException e) {
            Log.e("APOPapp", "Credentials I/O Error", e);
        }


        return true;
    }


    //TODO
    // Organization username and password for login directory
    public static JSONObject makeMasterCredentialsJSON(String username, String org, String password) {

        JSONObject json = new JSONObject();

        try {
            json.put("username", username);
            json.put("org", org);
            json.put("password", Password.hashPassword(password));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }
}
