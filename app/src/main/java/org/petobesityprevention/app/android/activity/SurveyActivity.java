package org.petobesityprevention.app.android.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.petobesityprevention.app.android.R;
import org.petobesityprevention.app.android.activity.SubmissionActivity;

public class SurveyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        // Breed dropdown
        Spinner spinner = findViewById(R.id.id_breed);

        // Display "please select a species" when species unspecified
        ArrayAdapter<CharSequence> adapter_empty = ArrayAdapter.createFromResource(this, R.array.dropdown_empty, android.R.layout.simple_spinner_item);
        adapter_empty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // List of dog breeds
        ArrayAdapter<CharSequence> adapter_dog = ArrayAdapter.createFromResource(this, R.array.dropdown_dog, android.R.layout.simple_spinner_item);
        adapter_dog.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // List of cat breeds
        ArrayAdapter<CharSequence> adapter_cat = ArrayAdapter.createFromResource(this, R.array.dropdown_cat, android.R.layout.simple_spinner_item);
        adapter_cat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter_empty);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Item selected, set text to the value
                TextView spinnerText = findViewById(R.id.id_spinner_text);
                spinnerText.setText(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });

        TextView spinnerText = findViewById(R.id.id_spinner_text);
        spinnerText.setText("");

        // Species buttons
        RadioButton dogButton = findViewById(R.id.id_pet_type_dog);
        RadioButton catButton = findViewById(R.id.id_pet_type_cat);

        // Set dropdown to selected species
        dogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setAdapter(adapter_dog);
            }
        });
        catButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setAdapter(adapter_cat);
            }
        });

        // Submit button
        Button submit = findViewById(R.id.id_submit);
        // When clicked...
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get field values
                EditText petName = findViewById(R.id.id_pet_name);
                RadioGroup petTypeGroup = findViewById(R.id.id_pet_type_group);
                int petTypeID = petTypeGroup.getCheckedRadioButtonId();
                RadioButton petType = findViewById(petTypeID);
                Spinner breed = findViewById(R.id.id_breed);
                EditText age = findViewById(R.id.id_age);
                RadioGroup sexGroup = findViewById(R.id.id_sex_group);
                int sexID = sexGroup.getCheckedRadioButtonId();
                RadioButton sex = findViewById(sexID);
                SeekBar numDogs = findViewById(R.id.id_num_dogs);
                SeekBar numCats = findViewById(R.id.id_num_cats);
                SeekBar ownerWeight = findViewById(R.id.id_owner_weight);
                SeekBar bcss = findViewById(R.id.id_bcss);
                EditText weight = findViewById(R.id.id_weight);
                EditText medical = findViewById(R.id.id_medical);
                EditText comments = findViewById(R.id.id_comments);

                // Reset labels if previously invalid
                boolean validFields = true;
                TextView weight_label = findViewById(R.id.id_label_weight);
                weight_label.setTextColor(getResources().getColor(R.color.colorWhite));
                TextView age_label = findViewById(R.id.id_label_age);
                age_label.setTextColor(getResources().getColor(R.color.colorWhite));
                TextView breed_label = findViewById(R.id.id_label_breed);
                breed_label.setTextColor(getResources().getColor(R.color.colorWhite));
                TextView name_label = findViewById(R.id.id_label_pet_name);
                name_label.setTextColor(getResources().getColor(R.color.colorWhite));

                // Check age and weight are numbers
                double ageNum = 0.0;
                double weightNum = 0.0;
                try {
                    ageNum = Double.parseDouble(age.getText().toString());
                    weightNum = Double.parseDouble(weight.getText().toString());
                } catch (NumberFormatException n) {
                    // Invalid weight or age entry
                    validFields = false;
                }

                // Check fields exist and are valid
                if (petName.getText().toString().equals("")) {
                    name_label.setTextColor(Color.RED);
                    validFields = false;
                }
                if (petType == null) {
                    breed_label.setTextColor(Color.RED);
                    validFields = false;
                }
                if (sex == null) {
                    // TODO make highlighted
                    validFields = false;
                }
                if (weight.getText().toString().equals("") || weightNum < 1 || weightNum > 350) {
                    weight_label.setTextColor(Color.RED);
                    validFields = false;
                }
                if (age.getText().toString().equals("") || ageNum < 1 || ageNum > 35) {
                    age_label.setTextColor(Color.RED);
                    validFields = false;
                }


                if (validFields) {
                    // Set the field values to pass to the submission activity
                    Intent submissionActivity = new Intent(getApplicationContext(), SubmissionActivity.class);
                    submissionActivity.putExtra("PET_NAME", petName.getText().toString());
                    submissionActivity.putExtra("PET_TYPE", petType.getText().toString());
                    submissionActivity.putExtra("BREED", breed.getSelectedItem().toString());
                    submissionActivity.putExtra("AGE", Double.parseDouble(age.getText().toString()));
                    submissionActivity.putExtra("WEIGHT", Double.parseDouble(weight.getText().toString()));
                    submissionActivity.putExtra("SEX", sex.getText().toString());
                    submissionActivity.putExtra("NUM_DOGS", numDogs.getProgress());
                    submissionActivity.putExtra("NUM_CATS", numCats.getProgress());
                    submissionActivity.putExtra("OWNER_WEIGHT", Integer.parseInt(String.valueOf(ownerWeight.getProgress() + 1)));
                    submissionActivity.putExtra("BCSS", Integer.parseInt(String.valueOf(bcss.getProgress() + 1)));
                    submissionActivity.putExtra("MEDICAL", medical.getText().toString());
                    submissionActivity.putExtra("COMMENTS", comments.getText().toString());

                    // Goto Submission Activity
                    startActivity(submissionActivity);
                }

                // Else
                // Scroll to top of page
                ScrollView scroll = findViewById(R.id.id_scrollView);
                scroll.fullScroll(ScrollView.FOCUS_UP);
            }
        });
    }
}