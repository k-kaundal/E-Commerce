package com.whlinks.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.whlinks.e_commerce.ui.activity.HomeActivity;
import com.whlinks.e_commerce.ui.activity.user.UserHomeActivity;
import com.whlinks.e_commerce.ui.auth.LoginActivity;

public class SplashScreenActivity extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    int userType = 1;
    @Override
    protected void onStart() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (user != null) {
//            assert user != null;
                    firebaseFirestore.collection("User").document(user.getUid().toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot documentSnapshots = task.getResult();
                            userType = Integer.parseInt(documentSnapshots.get("userType").toString());
                            if (userType == 0) {
                                Intent intent = new Intent(SplashScreenActivity.this, UserHomeActivity.class);
                                startActivity(intent);
                                finish();
                            } else if (userType == 1) {
                                Intent intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }

                        }
                    });
                } else {
                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);
        super.onStart();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }
}