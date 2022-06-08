package com.whlinks.e_commerce.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.whlinks.e_commerce.R;
import com.whlinks.e_commerce.service.CommonDBCall;
import com.whlinks.e_commerce.utils.Validations;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText email, password;
    Button login;
    TextView register, forgotpassword;
    CommonDBCall commonDBCall = new CommonDBCall();
    CheckBox rMe;
    int save=0;



    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sh = getSharedPreferences("login", MODE_APPEND);
// The value will be default as empty string because for
// the very first time when the app is opened, there is nothing to show
        String emailSP = sh.getString("email", "");
        String passSP = sh.getString("password", "");
// We can then use the data
        email.setText(emailSP);
        password.setText(passSP);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.whlinks.e_commerce.R.layout.activity_login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        forgotpassword = findViewById(R.id.forgotpassword);
        rMe = findViewById(R.id.rMe);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailText = email.getText().toString().trim();
                String passwordText = password.getText().toString().trim();
                if(rMe.isChecked()){
                    save = 1;
                }
                if (!Validations.isValidEmail(emailText)) {
                    email.setError("Enter valid email");
                    return;
                } else if (!Validations.isValidPassword(passwordText)) {
                    password.setError("Enter valid password");
                    return;
                }
                else {
                    commonDBCall.login(emailText, passwordText, LoginActivity.this, save);
                    finish();
                }

            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}