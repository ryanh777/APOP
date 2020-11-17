package org.petobesityprevention.app.android.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.petobesityprevention.app.android.R;
import org.petobesityprevention.app.android.user.Credentials;
import org.petobesityprevention.app.android.user.Hash;

public class AccountCreationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);

        //TODO
        // get values from creation view
        // CHANGE THIS
        String org = "TestOrg";
        String username = "test";
        String password = "password";
        String address = "201 S Columbia St, Chapel Hill, NC 27599";
        String phone = "+0 (000) 000-0000";
        String email = "apop.aws.manager@gmail.com";

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