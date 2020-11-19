package org.petobesityprevention.app.android.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.petobesityprevention.app.android.R;
import org.petobesityprevention.app.android.files.CallbackActivityDownloader;
import org.petobesityprevention.app.android.user.Credentials;
import org.petobesityprevention.app.android.user.Hash;

import java.io.File;

public class AccountCreationActivity extends AppCompatActivity {

    private String password, username, org, address, phone, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);

        // Download to check if username taken already
        CallbackActivityDownloader downloader = new CallbackActivityDownloader(this);
        downloader.getFileForCreation(username, "username.tmp", getApplicationContext());
    }

    // Username file
    public void callback() {
        Log.i("APOPappCreation", "Got called back!");

        File usernameCheckFile = new File(getApplicationContext().getFilesDir(), "username.tmp");

        if(usernameCheckFile.exists()) {
            // Username taken
        }
        else {
            // Hash the credentials to store the
            String hashedPassword = Hash.hashPassword(password);

            // establish information and register all credentials
            Credentials.setCredentials(username, org);
            Credentials.registerOrg(username, hashedPassword, org, address, phone, email, getApplicationContext());
            Credentials.registerDeviceToOrg(getApplicationContext());

            // Start survey
            Intent surveyActivity = new Intent(getApplicationContext(), SurveyActivity.class);
            startActivity(surveyActivity);
        }
    }
}