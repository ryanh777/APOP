package org.petobesityprevention.app.android.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.petobesityprevention.app.android.R;
import org.petobesityprevention.app.android.files.CallbackActivityDownloader;
import org.petobesityprevention.app.android.user.Credentials;
import org.petobesityprevention.app.android.user.Hash;

import java.io.File;

public class AccountCreationActivity extends AppCompatActivity {

    private String username, password, password2, org, address, phone, email;
    private View loadingSplash;
    private EditText usernameText;
    private boolean creating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);

        loadingSplash = findViewById(R.id.id_loadingPanel);
        AccountCreationActivity activity = this;

        creating = false;

        usernameText = findViewById(R.id.id_username);
        EditText pwText = findViewById(R.id.id_pw);
        EditText pw2Text = findViewById(R.id.id_pw2);
        EditText clinicText = findViewById(R.id.id_clinicName);
        EditText addressText = findViewById(R.id.id_address);
        EditText phoneText = findViewById(R.id.id_clinicPhone);
        EditText emailText = findViewById(R.id.id_clinicEmail);

        Button createButton = findViewById(R.id.id_create);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameText.getText().toString().toLowerCase();
                password = pwText.getText().toString();
                password2 = pw2Text.getText().toString();
                org = clinicText.getText().toString();
                address = addressText.getText().toString();
                phone = phoneText.getText().toString();
                email = emailText.getText().toString();

                if (username.equals("")) {
                    usernameText.setHint("Username Required");
                    usernameText.setHintTextColor(Color.RED);
                }
                if (password.equals("")) {
                    pwText.setHint("Password Required");
                    pwText.setHintTextColor(Color.RED);
                }
                if (!password.equals(password2)) {
                    //Password check confirmation failed
                    pw2Text.setText("");
                    pw2Text.setHint("Passwords Must Match");
                    pw2Text.setHintTextColor(Color.RED);
                } else {
                    // Download to check if username taken already
                    CallbackActivityDownloader downloader = new CallbackActivityDownloader(activity);
                    downloader.getFileForCreation("Orgs/"+ username + ".json", "username.tmp", getApplicationContext());

                    // Popup splash
                    loadingSplash.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    // Username file
    public void callback(boolean result) {

        loadingSplash.setVisibility(View.INVISIBLE);
        Log.i("APOPappCreation", "Got called back: " + result);

        File usernameCheckFile = new File(getApplicationContext().getFilesDir()+ "/username.tmp");

        if (usernameCheckFile.exists() || result) {
            // Username taken
            Log.e("APOPApp", "Username taken: " + username);

            usernameText.setText("");
            usernameText.setHint("Username Taken");
            usernameText.setHintTextColor(Color.RED);

            usernameCheckFile.delete();
        }
        else if(!creating) {

            creating = true;
        }
        else {
            Log.i("APOPApp", "Creating Account");


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