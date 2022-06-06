package com.whlinks.e_commerce.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whlinks.e_commerce.R;
import com.whlinks.e_commerce.adapter.ItemAdapter;
import com.whlinks.e_commerce.models.Item;
import com.whlinks.e_commerce.service.CommonDBCall;

import java.util.ArrayList;
import java.util.List;

public class AllItemFragment extends Fragment {


    public AllItemFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    CommonDBCall commonDBCall;
    List<Item> itemList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        commonDBCall = new CommonDBCall();
        commonDBCall.getAllItems();
        super.onStart();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_item, container, false);
        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        itemList = new ArrayList<>();
        for (int i = 0; i <= commonDBCall.itemList.size(); i++) {
//                    new Item(itemList[i].getData())
            System.out.println(commonDBCall.itemList.get(i).get("name"));
            itemList.add(new Item(commonDBCall.itemList.get(i).get("name").toString(), commonDBCall.itemList.get(i).get("descripton").toString(), commonDBCall.itemList.get(i).get("price").toString(), commonDBCall.itemList.get(i).get("image").toString()));
            System.out.println(commonDBCall.itemList);

        }
        recyclerView.setAdapter(new ItemAdapter(itemList, getContext()));

        return view;
    }
}