package org.petobesityprevention.app.android.files;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.petobesityprevention.app.android.user.Credentials;
import org.petobesityprevention.app.android.user.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Calendar;

public class Token {

    private static String tokenFileName = "token";
    private static String tokenCacheFileSuffix = ".tmp";
    private static String tokenMemoryFileSuffix = ".json";

    private static String tokenContents() throws JSONException {
        JSONObject userJSON = User.makeUserJSON(Credentials.getOrg(), Credentials.getDeviceID(), Credentials.getModel());

        // Set expiration date to one month from current date
        LocalDate expiration = LocalDate.now().plusMonths(1).plusDays(1);

        // add expiration date to json
        userJSON.put("expires", expiration);

        return userJSON.toString();
    }

    // write a token to the filesystem that lasts a desired amount of time
    public static void makeCacheToken(Context context) {

        try {
            File cacheToken = File.createTempFile(tokenFileName, tokenCacheFileSuffix, context.getCacheDir());
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(cacheToken));
            osw.write(tokenContents());
            osw.flush();
            osw.close();
            Log.i("APOP app", "Wrote cache token");
        }
        catch (JSONException j) { Log.e("APOP App", "Could not make token json info", j); }
        catch (FileNotFoundException f) { Log.e("APOP App", "Could not find token file", f); }
        catch (IOException e) { Log.e("APOP App", "Could not write to token file", e); }
    }

    // write a token to the filesystem that lasts a desired amount of time
    public static void makeFileToken(Context context) {

        File tokenFile = new File(context.getFilesDir(), "token.json");

        try {
            OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput(tokenFile.getName(), Context.MODE_PRIVATE));
            osw.write(tokenContents());
            osw.flush();
            osw.close();
            Log.i("APOP app", "Wrote file token");
        }
        catch (JSONException j) { Log.e("APOP App", "Could not make token json info", j); }
        catch (FileNotFoundException f) { Log.e("APOP App", "Could not find token file", f); }
        catch (IOException e) { Log.e("APOP App", "Could not write to token file", e); }
    }


    // Check if token exists set credentials if it's valid
    public static boolean tokenExistsAndIsValid(Context context) {

        File cacheFile = new File(context.getCacheDir(), tokenFileName.concat(tokenCacheFileSuffix));
        File memoryFile = new File(context.getFilesDir(), tokenFileName.concat(tokenMemoryFileSuffix));

        // Check cache file
        if (cacheFile.exists()) {
            try {
                return tokenContentValid(cacheFile ,context);
            } catch (IOException e) {
                // Error occurred when opening raw file for reading.
                Log.e("APOP App", "Could not read from token file", e);
            } catch (JSONException j) {
                // Error occurred when opening raw file for reading.
                Log.e("APOP App", "JSON error making object from token contents", j);
            }
        }
        else if (memoryFile.exists()) {
            try {
                return tokenContentValid(memoryFile ,context);
            } catch (IOException e) {
                // Error occurred when opening raw file for reading.
                Log.e("APOP App", "Could not read from token file", e);
            } catch (JSONException j) {
                // Error occurred when opening raw file for reading.
                Log.e("APOP App", "JSON error making object from token contents", j);
            }
        }
        else {
            return false;
        }
        return false;
    }

    // Check token content exists, delete if not
    private static boolean tokenContentValid(File file, Context context) throws JSONException, IOException
    {
        StringBuilder stringBuilder = new StringBuilder();
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(inputStreamReader);

        // Read contents
        String line = reader.readLine();
        while (line != null) {
            stringBuilder.append(line);
            line = reader.readLine();
        }
        String contents = stringBuilder.toString();

        // Make json object from contents
        JSONObject contentsJSON = new JSONObject(contents);

        // Check expiration
        LocalDate expiration = LocalDate.parse(contentsJSON.getString("expires"));

        // If still alive,
        if (LocalDate.now().isBefore(expiration)) {
            Log.i("APOPapp", "Token valid");

            // get contents
            String org = contentsJSON.getString("org");
            String device_id = contentsJSON.getString("device_id");
            String model = contentsJSON.getString("model");

            // Set credentials
            Credentials.setCredentialsFromToken(org, device_id, model);

            return true;
        }
        else {
            Log.i("APOPapp", "Token expired & deleted");
            // Dead, delete cache and memory files
            deleteFileToken(context);
            deleteCacheToken(context);
            return false;
        }}

    // Delete tokens as necessary (wrong info or expired)
    private static void deleteFileToken(Context context) {new File(context.getFilesDir(), tokenFileName.concat(tokenCacheFileSuffix)).delete();}
    private static void deleteCacheToken(Context context) {new File(context.getFilesDir(), tokenFileName.concat(tokenMemoryFileSuffix)).delete();}

}
