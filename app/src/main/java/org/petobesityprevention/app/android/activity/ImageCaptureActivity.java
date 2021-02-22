package org.petobesityprevention.app.android.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.petobesityprevention.app.android.R;
import org.petobesityprevention.app.android.camera.Camera2Fragment;

public class ImageCaptureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_capture);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, Camera2Fragment.newInstance())
                    .commit();
        }
    }

}