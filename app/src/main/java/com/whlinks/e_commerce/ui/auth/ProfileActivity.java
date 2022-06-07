package com.whlinks.e_commerce.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.whlinks.e_commerce.R;

public class ProfileActivity extends AppCompatActivity {
TextView name , email, phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name = findViewById(R.id.fName);
        email = findViewById(R.id.email);
        phone  = findViewById(R.id.phone);



    }
}