package org.petobesityprevention.app.android;

import android.content.Intent;
import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;

public class APOPapp extends android.app.Application {

    public void onCreate() {
        super.onCreate();

        try {
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.configure(getApplicationContext());
            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException ae) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", ae);
        }

        Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
        mainActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainActivity);
    }
}
