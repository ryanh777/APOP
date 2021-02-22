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

import java.io.File;

public class SurveyActivity extends AppCompatActivity {

    private final int REQUEST_CODE_MAIN = 123;
    private ImageView mImageView1;
    private ImageView mImageView2;
    private ImageView mImageView3;
    private LinearLayout mImageViews;
    private LinearLayout mImageButtons;
    private CardView mCamera;

    private boolean photosPresent = false;

    private File image_1;
    private File image_2;
    private File image_3;

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
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                // Item selected, set text to the value
                TextView spinnerText = findViewById(R.id.id_spinner_text);
                spinnerText.setText(adapterView.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });

        // Species buttons
        RadioButton dogButton = findViewById(R.id.id_pet_type_dog);
        RadioButton catButton = findViewById(R.id.id_pet_type_cat);

        // Number of _ seekbars
        SeekBar numDogsBar = findViewById(R.id.id_num_dogs);
        SeekBar numCatsBar = findViewById(R.id.id_num_cats);

        // Set dropdown and auto-increment counter of selected species
        dogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setAdapter(adapter_dog);
                numDogsBar.setProgress(1);
                numCatsBar.setProgress(0);
            }
        });
        catButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setAdapter(adapter_cat);
                numCatsBar.setProgress(1);
                numDogsBar.setProgress(0);
            }
        });


        Button imageButton = findViewById(R.id.id_image);
        imageButton.setOnClickListener(new ImageButtonRequestClickListener(123));

        Button mImageButton1 = findViewById(R.id.imageButton1);
        mImageButton1.setOnClickListener(new ImageButtonRequestClickListener(1));

        Button mImageButton2 = findViewById(R.id.imageButton2);
        mImageButton2.setOnClickListener(new ImageButtonRequestClickListener(2));

        Button mImageButton3 = findViewById(R.id.imageButton3);
        mImageButton3.setOnClickListener(new ImageButtonRequestClickListener(3));

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
                //Spinner breed = findViewById(R.id.id_breed);
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

                // Next move is to submission activity, first we need to send references to the data that is being confirmed
                Intent submissionActivity = new Intent(getApplicationContext(), SubmissionActivity.class);

                submissionActivity.putExtra("PHOTOS_PRESENT", photosPresent);

                if (photosPresent) {
                    submissionActivity.putExtra("IMAGE_1", image_1.getAbsolutePath());
                    submissionActivity.putExtra("IMAGE_2", image_2.getAbsolutePath());
                    submissionActivity.putExtra("IMAGE_3", image_3.getAbsolutePath());
                }

                if (validFields) {
                    // Set the field values to pass to the submission activity
                    submissionActivity.putExtra("PET_NAME", petName.getText().toString());
                    submissionActivity.putExtra("PET_TYPE", petType.getText().toString());
                    //submissionActivity.putExtra("BREED", breed.getSelectedItem().toString());
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

    // Image button click listener handles the ImageCaptureActivity start with the relevant photo number (1-3)
    class ImageButtonRequestClickListener implements View.OnClickListener {

        private final int requestCode;
        private final String codeValue;

        // Constructor initializes image id code for the image capture
        public ImageButtonRequestClickListener(int code) {
            this.requestCode = code;
            this.codeValue = "" + code;
        }

        @Override
        public void onClick(View v) {
            // Put the extra (photo id info) and start the image capture activity
            Intent imageCaptureActivity = new Intent(getApplicationContext(), ImageCaptureActivity.class);
            imageCaptureActivity.putExtra("ID", codeValue);
            startActivityForResult(imageCaptureActivity, requestCode);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        assert(data != null);

        // main image section, when all three are first taken
        if (requestCode == REQUEST_CODE_MAIN) {

            // update flag
            photosPresent = true;

            // set image files
            image_1 = new File(data.getStringExtra("path1"));
            image_2 = new File(data.getStringExtra("path2"));
            image_3 = new File(data.getStringExtra("path3"));

            // Set the image views for the photo previews on the survey screen
            mImageView1.setImageBitmap(BitmapFactory.decodeFile(
                    new File(data.getStringExtra("path1")).getAbsolutePath()));
            mImageView2.setImageBitmap(BitmapFactory.decodeFile(
                    new File(data.getStringExtra("path2")).getAbsolutePath()));
            mImageView3.setImageBitmap(BitmapFactory.decodeFile(
                    new File(data.getStringExtra("path3")).getAbsolutePath()));

            // Remove camera "take photos" button
            mCamera.setVisibility(View.GONE);

            // show the image previews and retake buttons
            mImageViews.setVisibility(View.VISIBLE);
            mImageButtons.setVisibility(View.VISIBLE);
        }

        // Subsequent individual image requests set the new image file path & update the view
        else if (requestCode == 1) {
            image_1 = new File(data.getStringExtra("path"));
            Bitmap bitmap = BitmapFactory.decodeFile(image_1.getAbsolutePath());
            mImageView1.setImageBitmap(bitmap);
        } else if (requestCode == 2) {
            File image_2 = new File(data.getStringExtra("path"));
            Bitmap bitmap = BitmapFactory.decodeFile(image_2.getAbsolutePath());
            mImageView2.setImageBitmap(bitmap);
        } else if (requestCode == 3) {
            File image_3 = new File(data.getStringExtra("path"));
            Bitmap bitmap = BitmapFactory.decodeFile(image_3.getAbsolutePath());
            mImageView3.setImageBitmap(bitmap);
        }
    }
}