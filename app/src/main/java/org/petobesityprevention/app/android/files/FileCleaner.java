package org.petobesityprevention.app.android.files;

import android.content.Context;
import android.util.Log;

import com.amplifyframework.core.Amplify;

import org.json.JSONException;
import org.json.JSONObject;
import org.petobesityprevention.app.android.user.User;

import java.io.File;
import java.io.IOException;

public class FileCleaner {

    public FileCleaner() {}

    private enum FileType {
        TOKEN, SURVEY, USER, ORG, CACHE, INDEF
    }

    public void cleanAndUploadAllFiles(Context context) {

        // list the app-created files
        File[] appFiles = context.getFilesDir().listFiles();
        if (appFiles != null) {
            // Upload all files and delete the ones that succeed
            for (File file : appFiles) {
                cleanFile(file);
            }
        }

        // Same for cache
        File[] cacheFiles = context.getCacheDir().listFiles();
        if (cacheFiles != null) {
            // Upload all files and delete the ones that succeed
            for (File file : cacheFiles) {
                cleanFile(file);
            }
        }
    }

    private void cleanFile(File file) {
        Log.i("APOPFileCleaner", "Cleaning file: " + file.getName());

        // File sanity check
        if (!file.isFile()) {
            return;
        }

        FileType fileType = getFileType(file);

        // Don't upload or remove tokens
        if (fileType.equals(FileType.TOKEN)) {
            return;
        }

        // Skip files that may be indefinite or have errors
        if (fileType.equals(FileType.INDEF)) {
            file.delete();
            return;
        }

        // In case of some random cache file, can do different things with it later
        if (fileType.equals(FileType.CACHE)) {
            file.delete();
            return;
        }

        String key = getFileKey(file, fileType);

        if (key == null) {
            return;
        }

        // Try upload and delete if successful
        Amplify.Storage.uploadFile(
                key,
                file,
                result -> {
                    Log.i("APOPapp", "Successfuly uploaded file in cleaning: " + result.getKey());
                    file.delete();
                },
                storageFailure -> {
                    Log.e("APOPapp", "Cleaning upload failed: " + key, storageFailure);
                });
    }

    private FileType getFileType(File file) {

        String name = file.getName();

        // Check for json file only
        if (!name.endsWith(".json")) {
            // .tmp is cache
            if (name.endsWith(".tmp")) { return CacheType(file); }
            else return FileType.INDEF;
        }

        // Check for survey
        if (name.substring(0, 7).equals("survey_") && name.endsWith(".json")) {
            return FileType.SURVEY;
        }

        /* So at this point we have to trust that our file naming is consistent */
        switch (name) {
            case "token.json": return FileType.TOKEN;
            case "org.json": return FileType.ORG;
            case "user.json": return FileType.USER;
            default: return FileType.INDEF;
        }
    }

    private FileType CacheType(File file) {
        if (file.getName().startsWith("token")) { return FileType.TOKEN; }
        else return FileType.CACHE;
    }

    private String getFileKey(File file, FileType fileType) {
        String key = null;

        switch (fileType) {
            case ORG: key = getOrgFileKey(file); break;
            case USER: key = getUserFileKey(file); break;
            case SURVEY: key = getSurveyFileKey(file); break;
            default: break;
        }
        return key;
    }

    private String getOrgFileKey(File file) {
        try {
            JSONObject orgJSON = JSONFile.fileToJSON(file);
            String username = orgJSON.getString("username");
            return "Orgs/" + username + ".json";
        } catch (IOException i) {
            Log.e("APOPapp", "IO OrgFile error in cleanup ", i);
        } catch (JSONException j) {
            Log.e("APOPapp", "JSON OrgFile error in cleanup ", j);
        }
        return null;
    }

    private String getUserFileKey(File file) {
        try {
            JSONObject userJSON = JSONFile.fileToJSON(file);
            String deviceID = userJSON.getString("device_id");
            String model = userJSON.getString("model");

            return "Users/" + User.makeUserID(deviceID, model) + ".json";
        } catch (IOException i) {
            Log.e("APOPapp", "IO UserFile error in cleanup ", i);
        } catch (JSONException j) {
            Log.e("APOPapp", "JSON UserFile error in cleanup ", j);
        }
        return null;
    }

    private String getSurveyFileKey(File file) {
        return "Surveys/" + file.getName().substring(7);
    }
}
