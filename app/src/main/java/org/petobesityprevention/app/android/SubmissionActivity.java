package org.petobesityprevention.app.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class SubmissionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission);

        String petName = getIntent().getExtras().getString("PET_NAME");
        String petType = getIntent().getExtras().getString("PET_TYPE");
        String breed = getIntent().getExtras().getString("BREED");
        String age = getIntent().getExtras().getString("AGE");
        String gender = getIntent().getExtras().getString("GENDER");
        String numDogs = getIntent().getExtras().getString("NUM_DOGS");
        String numCats = getIntent().getExtras().getString("NUM_CATS");
        String ownerWeight = getIntent().getExtras().getString("OWNER_WEIGHT");
        String bcss = getIntent().getExtras().getString("BCSS");
        String weight = getIntent().getExtras().getString("WEIGHT");
        String medical = getIntent().getExtras().getString("MEDICAL");
        String comments = getIntent().getExtras().getString("COMMENTS");


        TextView tv = findViewById(R.id.id_test_data_pass);
        tv.setText("Pet Name: " + petName + '\n'
                + "Pet Type: " + petType + '\n'
                + "Breed: " + breed + '\n'
                + "Age: " + age + '\n'
                + "Gender: " + gender + '\n'
                + "Number of Dogs in Household: " + numDogs + '\n'
                + "Number of Cats in Household: " + numCats + '\n'
                + "Owner Weight Assessment: " + ownerWeight + '\n'
                + "Body Condition Scoring System: " + bcss + '\n'
                + "Weight: " + weight + '\n'
                + "Previous Medical Conditions: " + medical + '\n'
                + "Comments: " + comments + '\n');

        Button submit = findViewById(R.id.id_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // make JSON from submission data and upload
                
                // TODO get this from credentials
                String org = Credentials.getOrg();
                String deviceID = Credentials.getDeviceID();
                
                JSONObject submissionJSON = JSONFactory.makeSurveyJSON(org, deviceID, petName, petType, breed, age, gender, numDogs, numCats, ownerWeight, bcss, weight, medical, comments);
                SurveySubmitter.uploadFile(getApplicationContext(), submissionJSON);

                Intent surveyActivity = new Intent(getApplicationContext(), SurveyActivity.class);
                startActivity(surveyActivity);
            }
        });
    }
}