package com.example.phongphung.appdevicesshopping.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.phongphung.appdevicesshopping.R;
import com.example.phongphung.appdevicesshopping.model.Product;
import com.squareup.picasso.Picasso;

import java.net.ContentHandler;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class LaptopAdapter extends BaseAdapter{
    private ArrayList<Product> arrLaptop;
    private Context context;

    public LaptopAdapter(ArrayList<Product> arrLaptop, Context context) {
        this.arrLaptop = arrLaptop;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrLaptop.size();
    }

    @Override
    public Object getItem(int position) {
        return arrLaptop.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public class ViewHolder{
        private TextView tvNameLaptop, tvPriceLaptop, tvDescLaptop;
        private ImageView imgLaptop;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(viewHolder == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_laptop, null);

            viewHolder.tvNameLaptop = view.findViewById(R.id.tv_nameLaptop);
            viewHolder.tvPriceLaptop = view.findViewById(R.id.tv_priceLaptop);
            viewHolder.tvDescLaptop = view.findViewById(R.id.tv_descLaptop);
            viewHolder.imgLaptop = view.findViewById(R.id.img_latop);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Product product = (Product) getItem(position);
        viewHolder.tvNameLaptop.setText(product.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.tvPriceLaptop.setText("Price: " + decimalFormat.format(product.getPrice()) + " đ");
        viewHolder.tvDescLaptop.setText(String.valueOf(product.getDesciption()));
        viewHolder.tvDescLaptop.setEllipsize(TextUtils.TruncateAt.END);
        Glide.with(context).load(product.getImage()).placeholder(R.drawable.image).error(R.drawable.error).into(viewHolder.imgLaptop);
//        Picasso.with(context).load(product.getImage()).placeholder(R.drawable.image).error(R.drawable.error).into(viewHolder.imgLaptop);
        return view;
    }
}
