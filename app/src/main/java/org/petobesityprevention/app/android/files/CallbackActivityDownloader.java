package org.petobesityprevention.app.android.files;

import android.content.Context;
import android.util.Log;


import com.amplifyframework.core.Amplify;
import com.amplifyframework.storage.options.StorageDownloadFileOptions;

import org.petobesityprevention.app.android.activity.MainActivity;

import java.io.File;


public class CallbackActivityDownloader {

    private final MainActivity activity;

    public CallbackActivityDownloader(MainActivity main) {
        this.activity = main;
    }

    /*
    * THIS WAS HELL
    * AMPLIFY DOCUMENTATION SUCKS
    * Downloading a file is an asynchronous event that somehow reserves itself to always execute on the main thread ONLY WHEN IDLE
    * So you can't: thread for it and wait, call a future and get that, implement a countdownlock, or countless other things I tried that should work
    * Amazing
    * This approach mitigates that by making the main thread will be idle once the call is made, and we callback execution once the result is ready
    * */

    public void getFile(String key, String outputName, Context context) {
        Amplify.Storage.downloadFile(key,
                new File(context.getFilesDir() + "/" + outputName),
                StorageDownloadFileOptions.defaultInstance(),
                progress -> Log.i("APOPApp", "Fraction completed: " + progress.getFractionCompleted()),
                result -> { // Success
                    Log.i("APOPApp", "Download success");
                    activity.callback();
                },
                error -> { // Error
                    Log.e("APOPapp", "Download failed");
                    activity.callback();
                });
    }
}