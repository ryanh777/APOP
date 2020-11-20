package org.petobesityprevention.app.android.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import org.petobesityprevention.app.android.R;
import org.petobesityprevention.app.android.activity.SubmissionActivity;

import java.io.File;

public class SurveyActivity extends AppCompatActivity {

    private Integer REQUEST_CODE = 123;
    private ImageView mImageView1;
    private ImageView mImageView2;
    private ImageView mImageView3;
    private LinearLayout mImageViews;
    private LinearLayout mImageButtons;
    private Button mImageButton1;
    private Button mImageButton2;
    private Button mImageButton3;
    private CardView mCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        mImageView1 = findViewById(R.id.id_imageview_1);
        mImageView2 = findViewById(R.id.id_imageview_2);
        mImageView3 = findViewById(R.id.id_imageview_3);
        mImageViews = findViewById(R.id.id_imageviews);
        mImageButtons = findViewById(R.id.id_image_buttons);
        mCamera = findViewById(R.id.id_image_card);

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

        Button imageButton = findViewById(R.id.id_image);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imageCaptureActivity = new Intent(getApplicationContext(), ImageCaptureActivity.class);
                imageCaptureActivity.putExtra("ID", "123");
                startActivityForResult(imageCaptureActivity, REQUEST_CODE);
            }
        });

        mImageButton1 = findViewById(R.id.imageButton1);
        mImageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imageCaptureActivity = new Intent(getApplicationContext(), ImageCaptureActivity.class);
                imageCaptureActivity.putExtra("ID", "1");
                startActivityForResult(imageCaptureActivity, 1);
            }
        });

        mImageButton2 = findViewById(R.id.imageButton2);
        mImageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imageCaptureActivity = new Intent(getApplicationContext(), ImageCaptureActivity.class);
                imageCaptureActivity.putExtra("ID", "2");
                startActivityForResult(imageCaptureActivity, 2);
            }
        });

        mImageButton3 = findViewById(R.id.imageButton3);
        mImageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imageCaptureActivity = new Intent(getApplicationContext(), ImageCaptureActivity.class);
                imageCaptureActivity.putExtra("ID", "3");
                startActivityForResult(imageCaptureActivity, 3);
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
                TextView sex_label = findViewById(R.id.id_sex_invalid);
                name_label.setVisibility(View.INVISIBLE);

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
                if (petName.getText().toString().trim().equals("")) {
                    name_label.setTextColor(Color.RED);
                    validFields = false;
                }
                if (petType == null) {
                    breed_label.setTextColor(Color.RED);
                    validFields = false;
                }
                if (sex == null) {
                    sex_label.setVisibility(View.VISIBLE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            //mComments.setText("dude");
            //mComments.setText(data.getStringExtra("test"));
//            Bitmap bitmap1 = BitmapFactory.decodeByteArray(data.getByteArrayExtra("byteArray1"),0,data.getByteArrayExtra("byteArray1").length);
//            Bitmap bitmap2 = BitmapFactory.decodeByteArray(data.getByteArrayExtra("byteArray2"),0,data.getByteArrayExtra("byteArray2").length);
//            Bitmap bitmap3 = BitmapFactory.decodeByteArray(data.getByteArrayExtra("byteArray3"),0,data.getByteArrayExtra("byteArray3").length);

            File file1 = new File(data.getStringExtra("path1"));
            File file2 = new File(data.getStringExtra("path2"));
            File file3 = new File(data.getStringExtra("path3"));

            Bitmap bitmap1 = BitmapFactory.decodeFile(file1.getAbsolutePath());
            Bitmap bitmap2 = BitmapFactory.decodeFile(file2.getAbsolutePath());
            Bitmap bitmap3 = BitmapFactory.decodeFile(file3.getAbsolutePath());

            mImageView1.setImageBitmap(bitmap1);
            mImageView2.setImageBitmap(bitmap2);
            mImageView3.setImageBitmap(bitmap3);
            mCamera.setVisibility(View.GONE);
            mImageViews.setVisibility(View.VISIBLE);
            mImageButtons.setVisibility(View.VISIBLE);
        } else if (requestCode == 1) {
            File file = new File(data.getStringExtra("path"));
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            mImageView1.setImageBitmap(bitmap);
        } else if (requestCode == 2) {
            File file = new File(data.getStringExtra("path"));
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            mImageView2.setImageBitmap(bitmap);
        } else if (requestCode == 3) {
            File file = new File(data.getStringExtra("path"));
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            mImageView3.setImageBitmap(bitmap);
        }
    }
}