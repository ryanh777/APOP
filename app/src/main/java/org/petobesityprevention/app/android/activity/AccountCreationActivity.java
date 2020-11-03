package org.petobesityprevention.app.android.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.petobesityprevention.app.android.R;

public class AccountCreationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);

        // need to establish credentials and call Credentials.registerDeviceToOrg(getActivityContext());
    }
}