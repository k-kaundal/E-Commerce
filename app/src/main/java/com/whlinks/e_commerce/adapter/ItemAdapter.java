package com.whlinks.e_commerce.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.whlinks.e_commerce.R;
import com.whlinks.e_commerce.models.Item;
import com.whlinks.e_commerce.ui.ItemActivity;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    List<Item> itemList;
    private  Context context;

    public ItemAdapter(List<Item> itemList1, Context context) {
        this.itemList = itemList1;
        this.context = context;
    }


    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view= layoutInflater.inflate(R.layout.single_item, parent, false);
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
//        holder.itemImage.setImageURI(Uri.parse(itemList.get(position).getImage()));
        Glide.with(context).load(itemList.get(position).getImage()).into(holder.itemImage);
//        holder.itemImage.set;
        holder.itemtxt.setText(itemList.get(position).getName());
        holder.itemPrice.setText(itemList.get(position).getPrice());
        holder.itemDesc.setText(itemList.get(position).getDescripton());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ItemActivity.class);
                intent.putExtra("doc_id", itemList.get(position).getDoc_id());
                intent.putExtra("item", "0");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImage;
        TextView itemtxt, itemDesc, itemPrice;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.itemImg);
            itemtxt = itemView.findViewById(R.id.itemName);
            itemDesc = itemView.findViewById(R.id.itemDesc);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            linearLayout = itemView.findViewById(R.id.layout_id);
        }
    }
}
