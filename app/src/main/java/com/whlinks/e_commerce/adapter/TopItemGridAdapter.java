package com.whlinks.e_commerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.whlinks.e_commerce.R;
import com.whlinks.e_commerce.models.Item;

public class TopItemGridAdapter extends BaseAdapter {
    private final Context mContext;
    private final Item[] items;

    public TopItemGridAdapter(Item[] items, Context context) {
        this.mContext = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    // 3
    @Override
    public long getItemId(int position) {
        return 0;
    }

    // 4
    @Override
    public Object getItem(int position) {
        return null;
    }

    // 5
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1
        final Item item = items[position];

        // 2
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.top_items, null);
        }

        // 3
        final ImageView imageView = (ImageView) convertView.findViewById(R.id.itemImg);
        final TextView nameTextView = (TextView) convertView.findViewById(R.id.itemName);
        final TextView authorTextView = (TextView) convertView.findViewById(R.id.itemDesc);
        final TextView price = (TextView) convertView.findViewById(R.id.itemPrice);

        // 4
        Glide.with(mContext).load(item.getImage()).into(imageView);
//        nameTextView.setText(mContext.getString(item.getName()));
//        price.setText(mContext.getString(item.getPrice()));

        return convertView;
    }
}
