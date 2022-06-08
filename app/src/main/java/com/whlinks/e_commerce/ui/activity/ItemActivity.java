package com.whlinks.e_commerce.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.whlinks.e_commerce.R;

public class ItemActivity extends AppCompatActivity {
    ImageView itemImage;
    TextView name, description, price;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    Intent intent;
    String doc_id;
    String itemType;

    @Override
    protected void onStart() {

        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.whlinks.e_commerce.R.layout.activity_item);
        itemImage = findViewById(R.id.itemImage);
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        price = findViewById(R.id.price);
        intent = getIntent();
        doc_id = intent.getStringExtra("doc_id");
        itemType = intent.getStringExtra("item");
        System.out.println(itemType);
        if (itemType.equals("0")) {
            System.out.println(doc_id);
            firebaseFirestore.collection("Items").document(doc_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                    System.out.println(task.getResult().getData().get("name").toString());
                    if (task.getResult().getData().get("name").toString() != null) {
                        name.setText(task.getResult().getData().get("name").toString());
                    }
                    if (task.getResult().getData().get("descripton").toString() != null) {
                        description.setText(task.getResult().getData().get("descripton").toString());
                    }
                    if (task.getResult().getData().get("price").toString() != null) {
                        price.setText(task.getResult().getData().get("price").toString());
                    }
                    if (task.getResult().getData().get("image") != null) {
                        Glide.with(ItemActivity.this).load(task.getResult().getData().get("image")).into(itemImage);
                    }

                }
            });
        } else {
            firebaseFirestore.collection("LatestItems").document(doc_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    System.out.println(doc_id);
                    System.out.println(task.getResult().getData().get("name").toString());
                    if (task.getResult().getData().get("name").toString() != null) {
                        name.setText(task.getResult().getData().get("name").toString());
                    }
                    if (task.getResult().getData().get("descripton").toString() != null) {
                        description.setText(task.getResult().getData().get("descripton").toString());
                    }
                    if (task.getResult().getData().get("price").toString() != null) {
                        price.setText(task.getResult().getData().get("price").toString());
                    }
                    if (task.getResult().getData().get("image") != null) {
                        Glide.with(ItemActivity.this).load(task.getResult().getData().get("image")).into(itemImage);
                    }
                }
            });
        }

    }
}