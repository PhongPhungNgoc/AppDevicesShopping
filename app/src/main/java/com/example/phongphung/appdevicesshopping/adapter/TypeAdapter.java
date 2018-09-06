package com.example.phongphung.appdevicesshopping.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.phongphung.appdevicesshopping.model.Type;
import com.example.phongphung.appdevicesshopping.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TypeAdapter extends BaseAdapter {

    private ArrayList<Type> arrType;
    private Context context;

    public TypeAdapter(ArrayList<Type> arrType, Context context) {
        this.arrType = arrType;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrType.size();
    }

    @Override
    public Object getItem(int position) {
        return arrType.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private class ViewHolder {
        private TextView tvNameType;
        private ImageView imgType;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_type, null);

            viewHolder.tvNameType = convertView.findViewById(R.id.tvNameType);
            viewHolder.imgType = convertView.findViewById(R.id.imgType);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Type type = (Type) getItem(position);
        viewHolder.tvNameType.setText(type.getNameType());
//        Picasso.with(context).load(type.getImageType()).placeholder(R.drawable.image).error(R.drawable.error).into(viewHolder.imgType);
        Glide.with(context).load(type.getImageType()).placeholder(R.drawable.image).error(R.drawable.error).into(viewHolder.imgType);
        return convertView;
    }
}
