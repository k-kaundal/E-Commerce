package com.whlinks.e_commerce.service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.whlinks.e_commerce.models.Item;
import com.whlinks.e_commerce.models.Users;
import com.whlinks.e_commerce.ui.HomeActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CommonDBCall {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseStorage storage;
    StorageReference storageRef;
    Users users;
    Item item;

    // Registration
    public void register(String email, String password, String fName, String lName, String phone, String gender, Context context) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isComplete()) {
                    firebaseUser = mAuth.getCurrentUser();
                    if (firebaseUser != null) {
                        users = new Users(email, fName, lName, phone, gender);
                        firebaseFirestore.collection("User").document(firebaseUser.getUid()).set(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent intent = new Intent(context, HomeActivity.class);
                                context.startActivity(intent);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, e + "", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(context, "User Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e + "", Toast.LENGTH_SHORT).show();
            }
        });

    }

    // Login

    public void login(String email, String password, Context context, int save) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isComplete()) {
                    // Storing data into SharedPreferences
                    if (save == 1) {
                        SharedPreferences sharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);
// Creating an Editor object to edit(write to the file)
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();

// Storing the key and its value as the data fetched from edittext
                        myEdit.putString("email", email);
                        myEdit.putString("password", password);
// Once the changes have been made,
// we need to commit to apply those changes made,
// otherwise, it will throw an error
                        myEdit.commit();
                    }

                    Intent intent = new Intent(context, HomeActivity.class);
                    context.startActivity(intent);

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }


    public void addItem(String name, String description, String price, Context context, ImageView imageView, String imageUrl) {
        firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
//            uploadImage(imageView);
            String doc_id = UUID.randomUUID().toString();
            item = new Item(name, description, price, imageUrl, doc_id);
            firebaseFirestore.collection("Items").document(doc_id).set(item).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Intent intent = new Intent(context, HomeActivity.class);
                    context.startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, e + "", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    public void addLatestItem(String name, String description, String price, Context context, ImageView imageView, String imageUrl) {
        firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
//            uploadImage(imageView);
            String doc_id = UUID.randomUUID().toString();
            item = new Item(name, description, price, imageUrl, doc_id);
            firebaseFirestore.collection("LatestItems").document(doc_id).set(item).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Intent intent = new Intent(context, HomeActivity.class);
                    context.startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, e + "", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}
