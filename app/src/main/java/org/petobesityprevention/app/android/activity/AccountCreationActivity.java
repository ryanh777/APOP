package org.petobesityprevention.app.android.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.AlertDialog;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.petobesityprevention.app.android.R;
import org.petobesityprevention.app.android.files.CallbackActivityDownloader;
import org.petobesityprevention.app.android.user.Credentials;
import org.petobesityprevention.app.android.user.Hash;

import java.io.File;

public class AccountCreationActivity extends AppCompatActivity {

    private String username, password, password2, org, address, phone, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);

        // Download to check if username taken already
        CallbackActivityDownloader downloader = new CallbackActivityDownloader(this);
        downloader.getFileForCreation(username, "username.tmp", getApplicationContext());

        EditText usernameText = findViewById(R.id.id_username);
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
                username = usernameText.getText().toString();
                password = pwText.getText().toString();
                password2 = pw2Text.getText().toString();
                org = clinicText.getText().toString();
                address = addressText.getText().toString();
                phone = phoneText.getText().toString();
                email = emailText.getText().toString();

                callback();
            }
        });

    }

    // Username file
    public void callback() {
        Log.i("APOPappCreation", "Got called back!");

        File usernameCheckFile = new File(getApplicationContext().getFilesDir(), "username.tmp");

        if(usernameCheckFile.exists()) {
            // Username taken
            Toast.makeText(AccountCreationActivity.this, "Username already exists.", Toast.LENGTH_SHORT).show();
        }
        else if (!password.equals(password2)) {
            //Password check confirmation failed
            Toast.makeText(AccountCreationActivity.this, "Password and Confirm Password fields must match.", Toast.LENGTH_SHORT).show();
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