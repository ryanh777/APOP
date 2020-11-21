package org.petobesityprevention.app.android.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.petobesityprevention.app.android.user.Credentials;
import org.petobesityprevention.app.android.R;
import org.petobesityprevention.app.android.survey.SurveyJSON;
import org.petobesityprevention.app.android.survey.SurveySubmitter;

public class SubmissionActivity extends AppCompatActivity {

    // Ugly warning suppression
    @SuppressLint("SetTextI18n")

    // Start
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission);

        // Get values from previous state
        String petName = getIntent().getExtras().getString("PET_NAME");
        String petType = getIntent().getExtras().getString("PET_TYPE");
        double age = getIntent().getExtras().getDouble("AGE");
        double weight = getIntent().getExtras().getDouble("WEIGHT");
        String sex = getIntent().getExtras().getString("SEX");
        String breed = getIntent().getExtras().getString("BREED");
        int numDogs = getIntent().getExtras().getInt("NUM_DOGS");
        int numCats = getIntent().getExtras().getInt("NUM_CATS");
        int ownerWeight = getIntent().getExtras().getInt("OWNER_WEIGHT");
        int bcss = getIntent().getExtras().getInt("BCSS");
        String medical = getIntent().getExtras().getString("MEDICAL");
        String comments = getIntent().getExtras().getString("COMMENTS");

        // Set the data confirmation text
        TextView tv = findViewById(R.id.id_test_data_pass);
        tv.setText("Pet Name: " + petName + '\n'
                + "Species: " + petType + '\n'
                + "Age: " + age + '\n'
                + "Weight: " + weight + '\n'
                + "Sex: " + sex + '\n'
                + "Breed: " + breed + '\n'
                + "Dogs in Household: " + numDogs + '\n'
                + "Cats in Household: " + numCats + '\n'
                + "Owner Weight Assessment: " + ownerWeight + '\n'
                + "Body Condition Score: " + bcss + '\n'
                + "Previous Medical Conditions: " + medical + '\n'
                + "Comments: " + comments + '\n');

        // add action to Submit button
        Button submit = findViewById(R.id.id_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            // make JSON from submission data and upload
            @Override
            public void onClick(View view) {

                // User info
                String org = Credentials.getOrg();
                String deviceID = Credentials.getDeviceID();

                // Round these fields
                double roundedAge = ((int) ((age + 0.05)*10) /10.0);
                int roundedWeight = (int) (weight + 0.5);

                // make JSON
                SurveyJSON submissionJSON = SurveyJSON.makeSurveyJSON(org, deviceID,
                        petName, petType, roundedAge, roundedWeight, sex, breed, numDogs, numCats, ownerWeight, bcss, medical, comments);

                // upload JSON file
                SurveySubmitter.uploadFile(getApplicationContext(), submissionJSON);

                // go to thank you screen
                Intent thankyouActivity = new Intent(getApplicationContext(), ThankYouActivity.class);
                startActivity(thankyouActivity);
            }
        });

        // go back to survey
        Button goBack = findViewById(R.id.id_go_back);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}