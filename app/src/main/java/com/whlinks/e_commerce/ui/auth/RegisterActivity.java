package com.whlinks.e_commerce.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.hbb20.CountryCodePicker;
import com.whlinks.e_commerce.R;
import com.whlinks.e_commerce.service.CommonDBCall;
import com.whlinks.e_commerce.utils.Validations;

public class RegisterActivity extends AppCompatActivity {
    TextInputEditText email, password, fName, lName, phone;
    Button register;
    TextView login;
    CountryCodePicker cc;
    RadioGroup gender;
    RadioButton rd;

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
        phone = findViewById(R.id.phone);
        cc = findViewById(R.id.countryCode);
        gender = findViewById(R.id.gender);
        int selectedGenderId = gender.getCheckedRadioButtonId();
        rd = findViewById(selectedGenderId);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailText = email.getText().toString().trim();
                String passwordText = password.getText().toString().trim();
                String fNameText = fName.getText().toString().trim();
                String lNameText = lName.getText().toString().trim();
                String phoneText = cc.getSelectedCountryCode() + phone.getText().toString().trim();


                if (!Validations.isValidEmail(emailText)) {
                    email.setError("Enter valid email ID");
//                    email.setFocusable();
                    return;
                } else if (!Validations.isValidPassword(passwordText)) {
                    password.setError("Enter valid password");
//                    password.setFocusable();
                    return;
                } else if (fNameText.isEmpty()) {
                    fName.setError("Enter your first name ");
                    return;
                } else if (lNameText.isEmpty()) {
                    lName.setError("Enter your last name ");
                    return;
                } else if (phoneText.isEmpty()) {
                    return;
                } else {
                    commonDBCall.register(emailText, passwordText, fNameText, lNameText, phoneText, "Male", RegisterActivity.this);
                    finish();
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