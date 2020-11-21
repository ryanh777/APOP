package org.petobesityprevention.app.android;

import android.content.Intent;
import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;

import org.petobesityprevention.app.android.activity.MainActivity;

public class APOPapp extends android.app.Application {

    // Start
    public void onCreate() {
        super.onCreate();

        // Initialize amplify for aws
        try {
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.configure(getApplicationContext());
            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException ae) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", ae);
        }

        // Start main activity (goes to login screen)
        Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
        mainActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainActivity);
    }
}
