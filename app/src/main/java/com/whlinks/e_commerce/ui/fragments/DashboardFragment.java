package com.whlinks.e_commerce.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.whlinks.e_commerce.R;

import java.util.List;


public class DashboardFragment extends Fragment {


    public DashboardFragment() {
        // Required empty public constructor
    }

    TextView users, items, latestItems, topItems;
    List<DocumentSnapshot> topItemDoc, usersDocs, latestItemDocs, itemsDocs;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        items = view.findViewById(R.id.items);
        users = view.findViewById(R.id.userid);
        latestItems = view.findViewById(R.id.latestItems);
        topItems = view.findViewById(R.id.topItems);



        firebaseFirestore.collection("User").get().addOnCompleteListener(task -> {
            if (task.isComplete()) {
                QuerySnapshot queryDocumentSnapshots = task.getResult();
                usersDocs = queryDocumentSnapshots.getDocuments();
                if(usersDocs!=null) {
                    String userCout = Integer.toString(usersDocs.size());
                    users.setText(userCout);
                }

            }
        });
        firebaseFirestore.collection("Items").get().addOnCompleteListener(task -> {
            if (task.isComplete()) {
                QuerySnapshot queryDocumentSnapshots = task.getResult();
                itemsDocs = queryDocumentSnapshots.getDocuments();
                if(itemsDocs!=null) {
                    String itemCout = Integer.toString(itemsDocs.size());
                    items.setText(itemCout);
                }

            }
        });
        firebaseFirestore.collection("LatestItems").get().addOnCompleteListener(task -> {
            if (task.isComplete()) {
                QuerySnapshot queryDocumentSnapshots = task.getResult();
                latestItemDocs = queryDocumentSnapshots.getDocuments();
                if(latestItemDocs!=null) {
                    String lCout = Integer.toString(latestItemDocs.size());
                    latestItems.setText(lCout);
                }

            }
        });
        firebaseFirestore.collection("TopItems").get().addOnCompleteListener(task -> {
            if (task.isComplete()) {
                QuerySnapshot queryDocumentSnapshots = task.getResult();
                topItemDoc = queryDocumentSnapshots.getDocuments();
                if(topItemDoc!=null) {
                    String tCout = Integer.toString(topItemDoc.size());
                    topItems.setText(tCout);
                }

            }
        });


        return view;
    }
}