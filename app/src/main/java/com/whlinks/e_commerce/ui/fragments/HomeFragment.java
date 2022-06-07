package com.whlinks.e_commerce.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.whlinks.e_commerce.R;
import com.whlinks.e_commerce.adapter.ItemAdapter;
import com.whlinks.e_commerce.adapter.LatestItemAdapter;
import com.whlinks.e_commerce.models.Item;
import com.whlinks.e_commerce.service.CommonDBCall;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerViewlatest, recyclerViewall;
    CommonDBCall commonDBCall = new CommonDBCall();
    List<Item> itemList1;
    List<Item> itemList2;
    List<DocumentSnapshot> itemList;
    List<DocumentSnapshot> itemList3;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    Item item;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        recyclerViewlatest = view.findViewById(R.id.latestitemrecyc);
        recyclerViewall = view.findViewById(R.id.allitemsrecyc);

//        itemList1.add(new Item("Demo","Demo","Demo","Demo"));
        firebaseFirestore.collection("LatestItems").get().addOnCompleteListener(task -> {
            if (task.isComplete()) {
                QuerySnapshot queryDocumentSnapshots = task.getResult();
//                    System.out.println(queryDocumentSnapshots.getDocuments());
                itemList3 = queryDocumentSnapshots.getDocuments();
//                System.out.println(itemList);
//                itemList1 = new ArrayList<>();
//                itemList1.add(new Item("Demo","Demo","Demo","Demo"));
                itemList2 = new ArrayList<>();
                for (int i = 0; i < task.getResult().size(); i++) {
//                    new Item(itemList[i].getData());
                    System.out.println(itemList3.get(i).getData().get("name"));

                    itemList2.add(new Item(itemList3.get(i).getData().get("name").toString(), itemList3.get(i).getData().get("descripton").toString(), itemList3.get(i).getData().get("price").toString(), itemList3.get(i).getData().get("image").toString()));
//
//                   itemList1.add(itemList.get(i).getData());
                    System.out.println(itemList3.get(i).getData().get("name"));
                }
                recyclerViewlatest.setHasFixedSize(true);
                recyclerViewlatest.setLayoutManager(new LinearLayoutManager(getContext()));
                if (itemList2 == null) {
                    System.out.println("No data");
                } else {
                    recyclerViewlatest.setAdapter(new LatestItemAdapter(itemList2, getContext()));
                }
//                recyclerViewlatest.setAdapter(new ItemAdapter(itemList1, getContext()));

            }
        });


        firebaseFirestore.collection("Items").get().addOnCompleteListener(task -> {
            if (task.isComplete()) {
                QuerySnapshot queryDocumentSnapshots = task.getResult();
//                    System.out.println(queryDocumentSnapshots.getDocuments());
                itemList = queryDocumentSnapshots.getDocuments();
//                System.out.println(itemList);
//                itemList1 = new ArrayList<>();
//                itemList1.add(new Item("Demo","Demo","Demo","Demo"));
                itemList1 = new ArrayList<>();
                for (int i = 0; i < task.getResult().size(); i++) {
//                    new Item(itemList[i].getData());
                    System.out.println(itemList.get(i).getData().get("name"));

                    itemList1.add(new Item(itemList.get(i).getData().get("name").toString(), itemList.get(i).getData().get("descripton").toString(), itemList.get(i).getData().get("price").toString(), itemList.get(i).getData().get("image").toString()));
//
//                   itemList1.add(itemList.get(i).getData());
                    System.out.println(itemList.get(i).getData().get("name"));
                }
                recyclerViewall.setHasFixedSize(true);
                recyclerViewall.setLayoutManager(new LinearLayoutManager(getContext()));

                if (itemList1 == null) {
                    System.out.println("No data");
                } else {
                    recyclerViewall.setAdapter(new ItemAdapter(itemList1, getContext()));
                }
//                recyclerViewall.setAdapter(new ItemAdapter(itemList1, getContext()));

            }
        });


        return view;
    }
}