package com.whlinks.e_commerce.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.whlinks.e_commerce.R;
import com.whlinks.e_commerce.service.CommonDBCall;
import com.whlinks.e_commerce.ui.auth.ForgotPasswordActivity;
import com.whlinks.e_commerce.ui.auth.LoginActivity;


public class AddItemFragment extends Fragment {
    ImageView itemImage;
    EditText itemName, itemDescription, itemPrice;
    Button add;
    CommonDBCall commonDBCall;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_item, container, false);
        itemImage = view.findViewById(R.id.itemImage);
        itemName = view.findViewById(R.id.name);
        itemDescription = view.findViewById(R.id.description);
        itemPrice = view.findViewById(R.id.price);
        add = view.findViewById(R.id.additem);
        commonDBCall = new CommonDBCall();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = itemName.getText().toString().trim();
                String description = itemDescription.getText().toString().trim();
                String price = itemPrice.getText().toString().trim();
                if (name.isEmpty()) {
                    itemName.setError("Enter item name");
                } else if (description.isEmpty()) {
                    itemDescription.setError("Enter item description");
                } else if (price.isEmpty()) {
                    itemPrice.setError("Enter item price");
                } else {
                    commonDBCall.addItem(name, description, price, getActivity());
                }
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}