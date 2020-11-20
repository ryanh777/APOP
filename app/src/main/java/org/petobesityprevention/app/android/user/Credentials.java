package org.petobesityprevention.app.android.user;

import android.content.Context;
import android.util.Log;

import com.amplifyframework.core.Amplify;

import org.json.JSONException;
import org.json.JSONObject;
import org.petobesityprevention.app.android.files.JSONFile;
import org.petobesityprevention.app.android.files.Token;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class Credentials {

    // Credentials fields
    private static Credentials credentials;
    private static String username;
    private static String org;
    private static String deviceID;
    private static String model;

    // Constructor
    private Credentials(String u, String o) {
        username = u;
        org = o;
        deviceID = UUID.randomUUID().toString(); // Generate unique user id (https://developer.android.com/training/articles/user-data-ids)
        model = android.os.Build.MODEL;
    }
    private Credentials(String o, String d, String m) {
        username = "TOKEN_LOGIN";
        org = o;
        deviceID = d;
        model = m;
    }

    // Setter
    public static void setCredentials(String u, String o) { credentials = new Credentials(u, o); }
    public static void setCredentialsFromToken(String o, String d, String m) { credentials = new Credentials(o, d, m); }

    // Getters
    public static Credentials getCredentials() { return credentials; }
    public static String getUsername() { return username; }
    public static String getOrg() { return org; }
    public static String getDeviceID() { return deviceID; }
    public static String getModel() { return model; }


    public static Boolean checkAndSetCredentials(String username, String password, Context context) {

        Log.i("APOPapp", "Checking for Temp Credentials File");

        if (username.isEmpty() || password.isEmpty()) {
            return false;
        }

        Boolean valid = null;

        // Default for organization in case we can't get the full value
        String organization = username;

        // File should exist at this point if download was successful
        File credentialsFile = new File(context.getFilesDir() + "/credentials.tmp");
        if (credentialsFile.exists()) {
            try {
                // Make JSON object from file contents
                JSONObject credJSON = JSONFile.fileToJSON(credentialsFile);

                // Delete temp file
                if (credentialsFile.delete()) { Log.i("APOPapp", "Credentials File deleted"); } else { Log.e("APOPapp", "Credentials File not deleted"); }

                // Get data from JSON object
                String storedHash = credJSON.getString("password");
                organization = credJSON.getString("org");

                // compare password hashes
                valid = Hash.validatePasswordHash(password, storedHash);

            } catch (FileNotFoundException f) {
                Log.e("APOPapp", "Credentials File not found", f);
            } catch (JSONException j) {
                // JSON Error, probably that file was read wrong or has no 'org' field
                Log.e("APOPapp", "Credentials JSON Error", j);
            }
        }
        else {
            Log.i("APOPapp", "Credentials File doesn't exist");
            return false;
        }

        if (valid != null && valid) {
            // Set credentials
            Credentials.setCredentials(username, organization);
            Log.i("APOPapp", "Credentials set (Org, Device_ID): (" + Credentials.getOrg() + ", " + Credentials.getDeviceID() + ")");
        }

        return valid;
    }


    // Register org upon account creation
    public static void registerOrg(String username, String hash, String org, String address, String phone, String email, Context context) {

        // Make file name from user info
        String orgKey = "Orgs/" + username + ".json";

        // Make the file and its JSON content
        File orgFile = new File(context.getFilesDir(), "org.json");
        JSONObject orgJSON = makeMasterCredentialsJSON(username, hash, org, address, phone, email);

        try {
            // Write the file
            FileWriter writer = new FileWriter(orgFile);
            writer.write(orgJSON.toString());
            writer.close();
        }
        catch (Exception ex) {
            Log.e("APOPapp", "Credentials file creation failed", ex);
            return; // Don't try to upload broken file
        }

        // Upload the file
        Amplify.Storage.uploadFile(
                orgKey,
                orgFile,
                result -> {
                    Log.i("APOPapp", "Successfuly uploaded credentials: " + result.getKey());
                    orgFile.delete();
                    },
                storageFailure -> {
                    Log.e("APOPapp", "Credentials upload failed", storageFailure);
                }
        );
    }

    // Register device upon account login, create tokens
    public static void registerDeviceToOrg(Context context) {

        // Make file name from user info
        String userKey = "Users/" + User.makeUserID(deviceID, model) + ".json";

        // Make the file and its JSON content
        File userFile = new File(context.getFilesDir(), "user.json");
        JSONObject userJSON = User.makeUserJSON(org, deviceID, model);

        try {
            // Write the file
            FileWriter writer = new FileWriter(userFile);
            writer.write(userJSON.toString());
            writer.close();
        } catch (IOException i) {
            Log.e("APOPapp", "Credentials file creation failed", i);
            return; // Don't try to upload broken file
        }

        // Upload the file
        Amplify.Storage.uploadFile(
                userKey,
                userFile,
                result -> {
                    Log.i("APOPapp", "Successfuly uploaded credentials: " + result.getKey());
                    userFile.delete();
                    },
                storageFailure -> {
                    Log.e("APOPapp", "Credentials upload failed", storageFailure);
                }
        );

        // Store account tokens...
        {
            // in cache
            new Thread() {
                @Override
                public void run() {
                    Token.makeCacheToken(context);
                }
            }.start();

            // in memory
            new Thread() {
                @Override
                public void run() {
                    Token.makeFileToken(context);
                }
            }.start();
        }
    }


    // Organization username and password for login directory
    public static JSONObject makeMasterCredentialsJSON(String username, String password, String org, String address, String phone, String email) {

        JSONObject json = new JSONObject();

        try {
            json.put("username", username);
            json.put("password", password);
            json.put("org", org);
            json.put("address", address);
            json.put("phone", phone);
            json.put("email", email);
        } catch (JSONException e) {
            Log.e("APOPapp", "Master credentials JSON creation failed", e);
        }

        return json;
    }
}
