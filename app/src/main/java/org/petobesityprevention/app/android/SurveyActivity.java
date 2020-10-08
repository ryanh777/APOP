package org.petobesityprevention.app.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SurveyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        Spinner spinner = findViewById(R.id.id_breed);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.test_dropdown, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView spinnerText = findViewById(R.id.id_spinner_text);
                spinnerText.setText(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button submit = findViewById(R.id.id_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText petName = findViewById(R.id.id_pet_name);
                RadioGroup petTypeGroup = findViewById(R.id.id_pet_type_group);
                int petTypeID = petTypeGroup.getCheckedRadioButtonId();
                RadioButton petType = findViewById(petTypeID);
                Spinner breed = findViewById(R.id.id_breed);
                EditText age = findViewById(R.id.id_age);
                RadioGroup genderGroup = findViewById(R.id.id_gender_group);
                int genderID = genderGroup.getCheckedRadioButtonId();
                RadioButton gender = findViewById(genderID);
                SeekBar numDogs = findViewById(R.id.id_num_dogs);
                SeekBar numCats = findViewById(R.id.id_num_cats);
                SeekBar ownerWeight = findViewById(R.id.id_owner_weight);
                SeekBar bcss = findViewById(R.id.id_bcss);
                EditText weight = findViewById(R.id.id_weight);
                EditText medical = findViewById(R.id.id_medical);
                EditText comments = findViewById(R.id.id_comments);

                Intent submissionActivity = new Intent(getApplicationContext(), SubmissionActivity.class);
                submissionActivity.putExtra("PET_NAME", petName.getText().toString());
                if (petType != null) {
                    submissionActivity.putExtra("PET_TYPE", petType.getText().toString());
                }
                submissionActivity.putExtra("BREED", breed.getSelectedItem().toString());
                submissionActivity.putExtra("AGE", age.getText().toString());
                if (gender != null) {
                    submissionActivity.putExtra("GENDER", gender.getText().toString());
                }
                submissionActivity.putExtra("NUM_DOGS", String.valueOf(numDogs.getProgress()));
                submissionActivity.putExtra("NUM_CATS", String.valueOf(numCats.getProgress()));
                submissionActivity.putExtra("OWNER_WEIGHT", String.valueOf(ownerWeight.getProgress() + 1));
                submissionActivity.putExtra("BCSS", String.valueOf(bcss.getProgress() + 1));
                submissionActivity.putExtra("WEIGHT", weight.getText().toString());
                submissionActivity.putExtra("MEDICAL", medical.getText().toString());
                submissionActivity.putExtra("COMMENTS", comments.getText().toString());
                startActivity(submissionActivity);
            }
        });
    }
}