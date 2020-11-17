package org.petobesityprevention.app.android.files;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class JSONFile {

    public static JSONObject fileToJSON(File file) throws FileNotFoundException, JSONException {
        Scanner reader = new Scanner(file);
        StringBuilder fileContent = new StringBuilder();
        Log.i("APOPapp", "Reading File: " + file.getName());

        // Parse file contents
        while (reader.hasNextLine()) {
            fileContent.append(reader.nextLine());
        }
        reader.close();

        // Build JSON from the file contents
        return new JSONObject(fileContent.toString());
    }

}
