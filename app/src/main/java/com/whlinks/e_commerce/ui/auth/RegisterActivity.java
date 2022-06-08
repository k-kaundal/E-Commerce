package com.whlinks.e_commerce.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
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
    ImageView userImage;
    String imageUrl = "";
    private static final int IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageTask<UploadTask.TaskSnapshot> uploadTask;
    FirebaseStorage storageReference ;

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
        userImage = findViewById(R.id.userImage);
        storageReference = FirebaseStorage.getInstance();

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImage();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedGenderId = gender.getCheckedRadioButtonId();
                rd = findViewById(selectedGenderId);
                System.out.println(rd);
                String emailText = email.getText().toString().trim();
                String passwordText = password.getText().toString().trim();
                String fNameText = fName.getText().toString().trim();
                String lNameText = lName.getText().toString().trim();
                String phoneText = cc.getSelectedCountryCode() + phone.getText().toString().trim();
                String gender = "";
                if (selectedGenderId == R.id.male) {
                    System.out.println("Male");
                    gender = "Male";
                } else if (selectedGenderId == R.id.female) {
                    gender = "Female";
                } else if (selectedGenderId == R.id.other) {
                    gender = "Other";
                }
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

                    commonDBCall.register(emailText, passwordText, fNameText, lNameText, phoneText, gender, RegisterActivity.this,imageUrl);
                    finish();
                }

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {
         ProgressDialog pd = new ProgressDialog(RegisterActivity.this);
        pd.setIndeterminateDrawable(getResources().getDrawable(R.drawable.background));

        pd.setMessage("Uploading...");
        pd.show();

        if (imageUri != null) {
            StorageReference fileReference = storageReference.getReference().child("Profile").child(System.currentTimeMillis()
                    + "." + getFileExtension(imageUri));

            uploadTask = fileReference.putFile(Uri.parse(String.valueOf(imageUri)));
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();
                        imageUrl = mUri;
                        Glide.with(RegisterActivity.this).load(mUri).into(userImage);
//                        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
//                        HashMap<String, Object> map = new HashMap<>();
//                        map.put("imageURL", "" + mUri);
//                        reference.updateChildren(map);
                        pd.dismiss();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        } else {
            Toast.makeText(RegisterActivity.this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();

            if (uploadTask != null && uploadTask.isInProgress()) {
                Toast.makeText(RegisterActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
            } else {
                uploadImage();
            }
        }
    }


}