package com.whlinks.e_commerce.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.whlinks.e_commerce.R;
import com.whlinks.e_commerce.models.Item;
import com.whlinks.e_commerce.service.CommonDBCall;
import com.whlinks.e_commerce.ui.activity.ItemActivity;

import java.util.ArrayList;
import java.util.List;


public class TopItemAdapter extends RecyclerView.Adapter<TopItemAdapter.ViewHolder> {

    List<Item> itemList;
    private Context context;
    CommonDBCall commonDBCall = new CommonDBCall();

    public TopItemAdapter(List<Item> itemList1, Context context) {
        this.itemList = itemList1;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.top_items, parent, false);
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item, parent,false);
        return new TopItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Uri uri = Uri.parse(itemList.get(position).getImage());
        Glide.with(context).load(itemList.get(position).getImage()).into(holder.itemImage);
//        holder.itemImage.setImageURI(Uri.parse(itemList.get(position).getImage()));
        holder.itemtxt.setText(itemList.get(position).getName());
        holder.itemPrice.setText(itemList.get(position).getPrice());
        holder.itemDesc.setText(itemList.get(position).getDescripton());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ItemActivity.class);
                intent.putExtra("doc_id", itemList.get(position).getDoc_id());
                intent.putExtra("item", "2");
                context.startActivity(intent);
            }
        });


        holder.addToFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commonDBCall.addItemToFavorite(itemList.get(position).getName(), itemList.get(position).getDescripton(), itemList.get(position).getPrice(), itemList.get(position).getImage(), itemList.get(position).getDoc_id(), context);
            }
        });

        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commonDBCall.addItemToCart(itemList.get(position).getName(), itemList.get(position).getDescripton(), itemList.get(position).getPrice(), itemList.get(position).getImage(), itemList.get(position).getDoc_id(), context);
            }
        });


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImage, addToFavorite;
        TextView itemtxt, itemDesc, itemPrice;
        LinearLayout linearLayout;
        Button addToCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.itemImg);
            itemtxt = itemView.findViewById(R.id.itemName);
            itemDesc = itemView.findViewById(R.id.itemDesc);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            linearLayout = itemView.findViewById(R.id.layout_id);
            addToCart = itemView.findViewById(R.id.addToCart);
            addToFavorite = itemView.findViewById(R.id.addToFavorite);
        }
    }

}

