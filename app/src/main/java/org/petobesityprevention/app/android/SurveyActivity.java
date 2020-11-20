package org.petobesityprevention.app.android;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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