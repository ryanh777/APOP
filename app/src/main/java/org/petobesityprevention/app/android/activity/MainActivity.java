package org.petobesityprevention.app.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.petobesityprevention.app.android.user.Credentials;
import org.petobesityprevention.app.android.user.Password;
import org.petobesityprevention.app.android.R;
import org.petobesityprevention.app.android.user.Token;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Start
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check for token
        if (Token.tokenExistsAndIsValid(getApplicationContext())) // stored account token exists and is valid
        {
            // go straight to survey
            Intent surveyActivity = new Intent(getApplicationContext(), SurveyActivity.class);
            //surveyActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Doing this prevents the back button from allowing sign in change
            startActivity(surveyActivity);
        }


        // Text fields
        EditText usernameText = findViewById(R.id.id_username);
        EditText passwordText = findViewById(R.id.id_password);

        // Login button
        Button loginButton = findViewById(R.id.id_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // When login clicked

                // Get login info
                String username = usernameText.getText().toString(); // should be organization-wide username
                String password = Password.hashPassword(passwordText.getText().toString());

                // check credentials
                boolean cred_valid = Credentials.checkCredentials(username, password, getApplicationContext());
                Log.i("APOPapp", "Credentials: " + cred_valid);

                if (cred_valid) {
                    // Credentials Valid

                    // Generate unique user id (https://developer.android.com/training/articles/user-data-ids)
                    String uniqueID = UUID.randomUUID().toString();

                    // Set credentials
                    Credentials.setCredentials(username, uniqueID);
                    Log.i("APOPapp", "Credentials set (Org, Device_ID): (" + Credentials.getOrg() + ", " + Credentials.getDeviceID() + ")");
                }

                // Start survey
                Intent surveyActivity = new Intent(getApplicationContext(), SurveyActivity.class);
                startActivity(surveyActivity);
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
}