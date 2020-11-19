package org.petobesityprevention.app.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.AlertDialog;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.petobesityprevention.app.android.R;
import org.petobesityprevention.app.android.user.Credentials;
import org.petobesityprevention.app.android.user.Hash;

public class AccountCreationActivity extends AppCompatActivity {

    private static String username = null;
    private static String pw;
    private static String pw2;
    private static String clinic;
    private static String address;
    private static String phone;
    private static String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);

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
                pw = pwText.getText().toString();
                pw2 = pw2Text.getText().toString();
                clinic = clinicText.getText().toString();
                address = addressText.getText().toString();
                phone = phoneText.getText().toString();
                email = emailText.getText().toString();

                boolean r = true;

                if (username.length() < 1 || pw.length() < 1 || pw2.length() < 1 || clinic.length() < 1 || address.length() < 1 || phone.length() < 1 || email.length() < 1) {
                    Toast.makeText(AccountCreationActivity.this, "Please enter information for all fields.", Toast.LENGTH_SHORT).show();
                    r = false;
                }

                //TODO: check to see if username is taken already

                //check to make sure passwords match
                if (!pw.equals(pw2)) {
                    Toast.makeText(AccountCreationActivity.this, "Password and Confirm Password fields must match.", Toast.LENGTH_SHORT).show();
                    r = false;
                }

                if (!(phone.length() == 10 || phone.length() == 11 && phone.startsWith("1"))) {
                    Toast.makeText(AccountCreationActivity.this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
                    r = false;
                }

                if (r) {
                    finishRegistration();
                }
            }
        });

        //TODO
        // get values from creation view
        // CHANGE THIS
//        String org = "TestOrg";
//        String username = "test";
//        String password = "password";
//        String address = "201 S Columbia St, Chapel Hill, NC 27599";
//        String phone = "+0 (000) 000-0000";
//        String email = "apop.aws.manager@gmail.com";

    }

    private void finishRegistration(){
        // Hash the credentials to store the
        String hashedPassword = Hash.hashPassword(pw);

        // establish information and register all credentials
        Credentials.setCredentials(username, clinic);
        Credentials.registerOrg(username, hashedPassword, clinic, address, phone, email, getApplicationContext());
        Credentials.registerDeviceToOrg(getApplicationContext());

        // Start survey
        Intent surveyActivity = new Intent(getApplicationContext(), SurveyActivity.class);
        startActivity(surveyActivity);
    }

}