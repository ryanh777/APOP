package org.petobesityprevention.app.android.user;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Token {

    //TODO
    // write a token to the filesystem that lasts a desired amount of time
    public static void makeCacheToken(Context context) {

        String fileName = "token";
        String suffix = ".tmp";

        String fileContents = User.makeUserJSON(Credentials.getOrg(), Credentials.getDeviceID(), Credentials.getModel()).toString();

        try {
            File cacheToken = File.createTempFile(fileName, suffix, context.getCacheDir());
        }
        catch (FileNotFoundException f) { Log.e("APOP App", "Could not make token file", f); }
        catch (IOException e) { Log.e("APOP App", "Could not write to token file", e); }
    }

    //TODO
    // write a token to the filesystem that lasts a desired amount of time
    public static void makeFileToken(Context context) {

        String fileName = "token";

        String fileContents = User.makeUserJSON(Credentials.getOrg(), Credentials.getDeviceID(), Credentials.getModel()).toString();

        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(fileContents.getBytes());
        }
        catch (FileNotFoundException f) { Log.e("APOP App", "Could not make token file", f); }
        catch (IOException e) { Log.e("APOP App", "Could not write to token file", e); }
    }

    //TODO
    // Check if token exists
    public static boolean tokenExistsAndIsValid(Context context) {

        String fileName = "token";
        String cacheSuffix = ".tmp"; // MakeTempFile uses default suffix .tmp

        File cacheFile = new File(context.getCacheDir(), fileName.concat(cacheSuffix));

        // Check cache file

        // If exists,
        // Check time to live

        // If still alive,
        // Check contents

        // If contents valid,
        // return true;

        // Else dead
        // Delete cache file
        // Return false
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis, StandardCharsets.UTF_8);

            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }
        } catch (IOException e) {
            // Error occurred when opening raw file for reading.
        } finally {
            String contents = stringBuilder.toString();
        }

        return false;
    }

    //TODO
    // Delete tokens as necessary (wrong info or expired)
    private void deleteFileToken() {}
    private void deleteCacheToken() {}

}
