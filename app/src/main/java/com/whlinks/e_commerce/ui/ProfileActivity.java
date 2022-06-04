package com.whlinks.e_commerce.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.whlinks.e_commerce.R;

public class ProfileActivity extends AppCompatActivity {
    TextView textView,Name,LastName,Gender;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        textView=findViewById(R.id.fName);
        textView=findViewById(R.id.toolbar);

    }
}