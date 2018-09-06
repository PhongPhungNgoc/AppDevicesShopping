package com.example.phongphung.appdevicesshopping.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.phongphung.appdevicesshopping.R;
import com.example.phongphung.appdevicesshopping.model.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private ArrayList<Product> arrProductNew;
    private LayoutInflater inflater;
    private Context context;

    public ProductAdapter(ArrayList<Product> arrProductNew, Context context) {
        this.arrProductNew = arrProductNew;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_product_new, parent, false);
        ProductViewHolder productViewHolder = new ProductViewHolder(view);
        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = arrProductNew.get(position);
        holder.tvName.setText(product.getName());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvPrice.setText("Price: " + decimalFormat.format(product.getPrice()) + " đ");
//        Picasso.with(context).load(product.getImage()).placeholder(R.drawable.image).error(R.drawable.error).into(holder.imgProduct);
        Glide.with(context).load(product.getImage()).placeholder(R.drawable.image).error(R.drawable.error).into(holder.imgProduct);
    }

    @Override
    public int getItemCount() {
        return arrProductNew.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgProduct;
        public TextView tvName, tvPrice;

        public ProductViewHolder(View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.img_productNew);
            tvName = itemView.findViewById(R.id.tv_nameProductNew);
            tvPrice = itemView.findViewById(R.id.tv_priceProductNew);
        }
    }
}
