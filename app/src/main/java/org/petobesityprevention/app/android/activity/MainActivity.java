package org.petobesityprevention.app.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.petobesityprevention.app.android.files.CallbackActivityDownloader;
import org.petobesityprevention.app.android.files.FileCleaner;
import org.petobesityprevention.app.android.user.Credentials;
import org.petobesityprevention.app.android.R;
import org.petobesityprevention.app.android.files.Token;


public class MainActivity extends AppCompatActivity {

    private static String username = null, password = null;
    private EditText usernameText;
    private EditText passwordText;
    private TextView invalid_tag;
    private View loadingSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Start
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity main = this;
        loadingSplash = findViewById(R.id.id_loadingPanel);

        // Clean Files
        Log.i("APOPappMain", "Cleaning files");
        new Thread() {
            @Override
            public void run() {
                FileCleaner fileCleaner = new FileCleaner();
                fileCleaner.cleanAndUploadAllFiles(getApplicationContext());
            }
        }.start();

        // Check for token
        Log.i("APOPappMain", "Checking for token");
        if (Token.tokenExistsAndIsValid(getApplicationContext())) // stored account token exists and is valid
        {
            // go straight to survey
            Intent surveyActivity = new Intent(getApplicationContext(), SurveyActivity.class);
            //surveyActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Doing this prevents the back button from allowing sign in change
            startActivity(surveyActivity);
        }


        // Text fields
        usernameText = findViewById(R.id.id_username);
        passwordText = findViewById(R.id.id_password);

        invalid_tag = findViewById(R.id.id_invalid_tag);

        // Login button
        Button loginButton = findViewById(R.id.id_login);

        // When login clicked
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get login info
                username = usernameText.getText().toString(); // should be organization-wide username
                password = passwordText.getText().toString();

                // S3 File will be named this
                String key = "Orgs/" + username + ".json";

                // Download and save the file in cache, it will call back when finished
                CallbackActivityDownloader downloader = new CallbackActivityDownloader(main);

                Log.i("APOPappMain", "About to call download");
                downloader.getFileForMain(key, "credentials.tmp", getApplicationContext());
                Log.i("APOPappMain", "Called download");

                // Popup splash
                loadingSplash.setVisibility(View.VISIBLE);
            }
        });

        // Sign up button
        Button signupButton = findViewById(R.id.id_signup);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // When sign up clicked

                // Goto account creation
                Intent accountCreationActivity = new Intent(getApplicationContext(), AccountCreationActivity.class);
                startActivity(accountCreationActivity);
            }
        });

    }

    // Callback from downloader, means it has finished downloading credentials file
    public void callback(boolean result) {

        loadingSplash.setVisibility(View.INVISIBLE);

        Log.i("APOPappMain", "Got called back!");

        if (result) {
            // check credentials
            Boolean cred_valid = Credentials.checkAndSetCredentials(username, password, getApplicationContext());
            Log.i("APOPappMain", "Credentials: " + cred_valid);

            if (cred_valid == null) {
                // some error happened, set unknown credentials
                Credentials.setCredentials(username, "ORG_ERROR::USER_" + username);
            } else if (cred_valid) {
                // Login credentials valid

                // Register the device to the organization
                Credentials.registerDeviceToOrg(getApplicationContext());

                // Start survey
                Intent surveyActivity = new Intent(getApplicationContext(), SurveyActivity.class);
                startActivity(surveyActivity);
            } else {
                // Credentials invalid, clear fields and make the tag red
                invalid_tag.setVisibility(View.VISIBLE);
                passwordText.setText("");
            }
        }
        else {
            invalid_tag.setVisibility(View.VISIBLE);
            usernameText.setText("");
            passwordText.setText("");
        }
    }
}