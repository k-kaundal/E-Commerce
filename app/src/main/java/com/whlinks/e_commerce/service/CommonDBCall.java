package com.whlinks.e_commerce.service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.whlinks.e_commerce.SplashScreenActivity;
import com.whlinks.e_commerce.models.Item;
import com.whlinks.e_commerce.models.Users;
import com.whlinks.e_commerce.ui.activity.HomeActivity;
import com.whlinks.e_commerce.ui.activity.user.UserHomeActivity;
import com.whlinks.e_commerce.ui.auth.LoginActivity;

import java.util.UUID;

public class CommonDBCall {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseStorage storage;
    StorageReference storageRef;
    Users users;
    Item item;
    String doc_id = UUID.randomUUID().toString();
    // Registration
    public void register(String email, String password, String fName, String lName, String phone, String gender, Context context, String imageUrl) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isComplete()) {
                    firebaseUser = mAuth.getCurrentUser();
                    if (firebaseUser != null) {
                        users = new Users(email, fName, lName, phone, gender,imageUrl,1);
                        firebaseFirestore.collection("User").document(firebaseUser.getUid()).set(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent intent = new Intent(context, UserHomeActivity.class);
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
                    SharedPreferences sharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);
// Creating an Editor object to edit(write to the file)
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    if (save == 1) {
// Storing the key and its value as the data fetched from edittext
                        myEdit.putString("email", email);
                        myEdit.putString("password", password);
// Once the changes have been made,
// we need to commit to apply those changes made,
// otherwise, it will throw an error
                        myEdit.commit();
                    }else {
                        myEdit.clear();
                    }


//            assert user != null;
                        firebaseFirestore.collection("User").document(mAuth.getUid().toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                DocumentSnapshot documentSnapshots = task.getResult();
                        int        userType = Integer.parseInt(documentSnapshots.get("userType").toString());
                                if (userType == 1) {
                                    Intent intent = new Intent(context, UserHomeActivity.class);
                                    context.startActivity(intent);
//                                    context.finish();
                                } else if (userType == 0) {
                                    Intent intent = new Intent(context, HomeActivity.class);
                                    context.startActivity(intent);
//                                    finish();
                                } else {
                                    Intent intent = new Intent(context, LoginActivity.class);
                                    context.startActivity(intent);
//                                    finish();
                                }

                            }
                        });

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

            item = new Item(name, description, "Rs "+price, imageUrl, doc_id);
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
            item = new Item(name, description, "Rs "+price, imageUrl, doc_id);
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
    public void addTopItem(String name, String description, String price, Context context, ImageView imageView, String imageUrl) {
        firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
//            uploadImage(imageView);
            item = new Item(name, description, "Rs "+price, imageUrl, doc_id);
            firebaseFirestore.collection("TopItems").document(doc_id).set(item).addOnCompleteListener(new OnCompleteListener<Void>() {
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
    public void removeTopItem(String dID,Context context) {
        firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
//            uploadImage(imageView);
//            item = new Item(name, description, "Rs "+price, imageUrl, doc_id);
            firebaseFirestore.collection("TopItems").document(dID).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
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
    public void removeLatestItem(String dID,Context context) {
        firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
//            uploadImage(imageView);
//            item = new Item(name, description, "Rs "+price, imageUrl, doc_id);
            firebaseFirestore.collection("LatestItems").document(dID).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
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
    public void removeItem(String dID,Context context) {
        firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
//            uploadImage(imageView);
//            item = new Item(name, description, "Rs "+price, imageUrl, doc_id);
            firebaseFirestore.collection("Items").document(dID).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
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


    public void addItemToCart(String name, String description, String price,String imageUrl, String dID, Context context){
        firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
//            uploadImage(imageView);
            item = new Item(name, description, price, imageUrl, dID);
            firebaseFirestore.collection("UserCollections").document(firebaseUser.getUid().toString()).collection("Cart").document(dID).set(item).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(context, name+" is added to your cart", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, UserHomeActivity.class);
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
    public void addItemToFavorite(String name, String description, String price,String imageUrl, String dID, Context context){
        firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
//            uploadImage(imageView);
            item = new Item(name, description, price, imageUrl, dID);
            firebaseFirestore.collection("UserCollections").document(firebaseUser.getUid().toString()).collection("Favorite").document(dID).set(item).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(context, name+" is added to your favorite items list", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, UserHomeActivity.class);
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
    public void addItemToLatestAdmin(String name, String description, String price,String imageUrl, String dID, Context context){
        firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
//            uploadImage(imageView);
            item = new Item(name, description, price, imageUrl, dID);
            firebaseFirestore.collection("LatestItems").document(dID).set(item).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(context, name+" is added to latest items list", Toast.LENGTH_SHORT).show();
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
    public void addItemToTopAdmin(String name, String description, String price,String imageUrl, String dID, Context context){
        firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
//            uploadImage(imageView);
            item = new Item(name, description, price, imageUrl, dID);
            firebaseFirestore.collection("TopItems").document(dID).set(item).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(context, name+" is added to top items list", Toast.LENGTH_SHORT).show();
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


    public void removeItemFromFavorite(String dID, Context context){
        firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
//            uploadImage(imageView);
//            item = new Item(name, description, price, imageUrl, dID);
            firebaseFirestore.collection("UserCollections").document(firebaseUser.getUid().toString()).collection("Favorite").document(dID).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(context, "Item removed favorite list", Toast.LENGTH_SHORT).show();
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



    public void removeItemFromCart(String dID, Context context){
        firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
//            uploadImage(imageView);
//            item = new Item(name, description, price, imageUrl, dID);
            firebaseFirestore.collection("UserCollections").document(firebaseUser.getUid().toString()).collection("Cart").document(dID).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(context, "Item removed from Cart", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, UserHomeActivity.class);
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
