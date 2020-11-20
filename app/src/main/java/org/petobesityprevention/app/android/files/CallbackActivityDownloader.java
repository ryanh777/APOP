package org.petobesityprevention.app.android.files;

import android.content.Context;
import android.util.Log;


import com.amplifyframework.core.Amplify;
import com.amplifyframework.storage.options.StorageDownloadFileOptions;

import org.petobesityprevention.app.android.activity.AccountCreationActivity;
import org.petobesityprevention.app.android.activity.MainActivity;

import java.io.File;


public class CallbackActivityDownloader {

    private final MainActivity main;
    private final AccountCreationActivity creation;

    public CallbackActivityDownloader(MainActivity activity) {
        this.main = activity;
        this.creation = null;
    }
    public CallbackActivityDownloader(AccountCreationActivity activity) {
        this.main = null;
        this.creation = activity;
    }

    /*
    * THIS WAS HELL
    * AMPLIFY DOCUMENTATION SUCKS
    * Downloading a file is an asynchronous event that somehow reserves itself to always execute on the main thread ONLY WHEN IDLE
    * So you can't: thread for it and wait, call a future and get that, implement a countdownlock, or countless other things I tried that should work
    * Amazing
    * This approach mitigates that by making sure the main thread will be idle once the call is made, and we callback execution once the result is ready
    * */

    public void getFileForMain(String key, String outputName, Context context) {
        Amplify.Storage.downloadFile(key,
                new File(context.getFilesDir() + "/" + outputName),
                result -> { // Success
                    Log.i("APOPAppDownloader", "Login Username Check Download success");
                    main.callback(true);
                },
                error -> { // Error
                    Log.e("APOPappDownloader", "Login Username Check Download failed");
                    main.callback(false);
                });
    }

    public void getFileForCreation(String key, String outputName, Context context) {
        Amplify.Storage.downloadFile(key,
                new File(context.getFilesDir() + "/" + outputName),
                result -> { // Success
                    Log.e("APOPAppDownloader", "Creation Username Check Download success");
                    creation.callback(true);
                },
                error -> { // Error
                    Log.i("APOPAppDownloader", "Creation Username Check Download Failed");
                    creation.callback(false);
                });
    }
}