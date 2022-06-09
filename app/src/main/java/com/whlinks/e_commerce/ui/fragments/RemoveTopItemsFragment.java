package com.whlinks.e_commerce.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.whlinks.e_commerce.R;
import com.whlinks.e_commerce.adapter.RemoveItemAdapter;
import com.whlinks.e_commerce.adapter.RemoveTopItem;
import com.whlinks.e_commerce.models.Item;
import com.whlinks.e_commerce.service.CommonDBCall;

import java.util.ArrayList;
import java.util.List;


public class RemoveTopItemsFragment extends Fragment {

    public RemoveTopItemsFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    CommonDBCall commonDBCall = new CommonDBCall();
    List<Item> itemList1;
    List<DocumentSnapshot> itemList;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    Item item;
    FirebaseAuth firebaseAuth ;
    FirebaseUser firebaseUser;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {

        super.onStart();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_remove_top_items, container, false);
        recyclerView = view.findViewById(R.id.recycler);
//        itemList1.add(new Item("Demo","Demo","Demo","Demo"));
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore.collection("TopItems").get().addOnCompleteListener(task -> {
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

                    itemList1.add(new Item(itemList.get(i).getData().get("name").toString(), itemList.get(i).getData().get("descripton").toString(), itemList.get(i).getData().get("price").toString(), itemList.get(i).getData().get("image").toString(), itemList.get(i).getData().get("doc_id").toString()));
//
//                   itemList1.add(itemList.get(i).getData());
                    System.out.println(itemList.get(i).getData().get("name"));
                }
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                if (itemList1 == null){
                    System.out.println("No data");
                }else{
                    recyclerView.setAdapter(new RemoveTopItem(itemList1, getActivity()));
                }


            }
        });
//        System.out.println(itemList1.size());


        return view;
    }
}