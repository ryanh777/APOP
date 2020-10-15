package org.petobesityprevention.app.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText username = findViewById(R.id.id_username);
        EditText password = findViewById(R.id.id_password);

        Button submitButton = findViewById(R.id.id_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // check credentials
                boolean cred_valid = Credentials.checkCredentials(getApplicationContext(), "Users/Example.json");
                Log.i("APOPapp", "Credentials: " + cred_valid);

                if (cred_valid) {
                    String uniqueID = UUID.randomUUID().toString();
                    Credentials.setCredentials(username.getText().toString(), uniqueID);
                    Log.i("APOPapp", "Credentials set (Org, Device_ID): (" + Credentials.getOrg() + ", " + Credentials.getDeviceID() + ")");
                }

                Intent surveyActivity = new Intent(getApplicationContext(), SurveyActivity.class);
                startActivity(surveyActivity);
            }
        });
    }
}