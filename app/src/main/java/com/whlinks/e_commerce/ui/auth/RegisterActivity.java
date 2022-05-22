package com.whlinks.e_commerce.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.whlinks.e_commerce.R;
import com.whlinks.e_commerce.service.CommonDBCall;
import com.whlinks.e_commerce.utils.Validations;

public class RegisterActivity extends AppCompatActivity {
 TextInputEditText email, password, fName, lName;
 Button register;
 TextView login;

 CommonDBCall commonDBCall = new CommonDBCall();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.whlinks.e_commerce.R.layout.activity_register);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        fName = findViewById(R.id.fName);
        lName = findViewById(R.id.lName);
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailText = email.getText().toString().trim();
                String passwordText = password.getText().toString().trim();
                if (!Validations.isValidEmail(emailText)){
                    email.setError("Enter valid email ID");
//                    email.setFocusable();
                    return;
                }else if(!Validations.isValidPassword(passwordText)){
                    password.setError("Enter valid password");
//                    password.setFocusable();
                    return;
                }else{
                    commonDBCall.register(emailText,passwordText, RegisterActivity.this);
                }
            }
        });



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}