package org.petobesityprevention.app.android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class ThankYouActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thankyou);
        Button submit = findViewById(R.id.id_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // clear stack and go to new survey
                Intent surveyActivity = new Intent(getApplicationContext(), SurveyActivity.class);
                surveyActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(surveyActivity);
            }
        });

        Button link = findViewById(R.id.id_link);

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // clear stack and go to new survey
                Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://petobesityprevention.org/"));
                startActivity(browser);
            }
        });


    }
}