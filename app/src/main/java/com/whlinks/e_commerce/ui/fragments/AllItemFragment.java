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

import java.util.ArrayList;
import java.util.List;

public class AllItemFragment extends Fragment {

    public AllItemFragment() {
        // Required empty public constructor
    }
    RecyclerView recyclerView;
    List<Item> itemList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    private  List<Item> initData(){
        itemList = new ArrayList<>();



        return itemList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_item, container, false);
        recyclerView = view.findViewById(R.id.layout_id);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(new ItemAdapter(initData(),getContext()));


        return view;
    }
}