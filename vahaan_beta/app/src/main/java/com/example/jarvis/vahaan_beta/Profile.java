package com.example.jarvis.vahaan_beta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Profile extends AppCompatActivity {
    private TextView txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        txtEmail = (TextView) findViewById(R.id.tvEmailProfile);
        txtEmail.setText(getIntent().getExtras().getString("Email"));
    }
}
