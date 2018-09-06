package com.example.phongphung.appdevicesshopping.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.phongphung.appdevicesshopping.R;
import com.example.phongphung.appdevicesshopping.activity.CartActivity;
import com.example.phongphung.appdevicesshopping.activity.MainActivity;
import com.example.phongphung.appdevicesshopping.model.Cart;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {
    private ArrayList<Cart> arrCart;
    private Context context;


    public CartAdapter(ArrayList<Cart> arrCart, Context context) {
        this.arrCart = arrCart;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrCart.size();
    }

    @Override
    public Object getItem(int position) {
        return arrCart.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public class ViewHolder {
        public TextView tvName, tvAmount;
        public ImageView imgProduct;
        public Button btnDecrease, btnIncrease, btnQuantity;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_cart, null);

            viewHolder.tvName = view.findViewById(R.id.tv_nameProductCart);
            viewHolder.tvAmount = view.findViewById(R.id.tv_amountCart);
            viewHolder.imgProduct = view.findViewById(R.id.img_productCart);
            viewHolder.btnDecrease = view.findViewById(R.id.btn_decrease);
            viewHolder.btnQuantity = view.findViewById(R.id.btn_quantity);
            viewHolder.btnIncrease = view.findViewById(R.id.btn_increase);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Cart cart = (Cart) getItem(position);
        viewHolder.tvName.setText(cart.getNameProduct());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.tvAmount.setText("Amount Product: " + decimalFormat.format(cart.getAmount()) + " Đ");
        Glide.with(context).load(cart.getImgProduct()).placeholder(R.drawable.image).error(R.drawable.error).into(viewHolder.imgProduct);
        viewHolder.btnQuantity.setText(String.valueOf(cart.getQuantity()));

        final int quantities = Integer.parseInt(viewHolder.btnQuantity.getText().toString());
        if (quantities == 10) {
            viewHolder.btnIncrease.setVisibility(View.INVISIBLE);
            viewHolder.btnDecrease.setVisibility(View.VISIBLE);
        } else if (quantities > 1) {
            viewHolder.btnDecrease.setVisibility(View.VISIBLE);
            viewHolder.btnIncrease.setVisibility(View.VISIBLE);
        } else if( quantities == 1 ){
            viewHolder.btnDecrease.setVisibility(View.INVISIBLE);
            viewHolder.btnIncrease.setVisibility(View.VISIBLE);
        }

        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantitiesNew = Integer.parseInt(finalViewHolder.btnQuantity.getText().toString()) + 1;

                int quantitiesNow = MainActivity.arrCart.get(position).getQuantity();
                long amountNow = MainActivity.arrCart.get(position).getAmount();

                MainActivity.arrCart.get(position).setQuantity(quantitiesNew);

                long amountNew = (amountNow * quantitiesNew) / quantitiesNow;
                MainActivity.arrCart.get(position).setAmount(amountNew);


                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.tvAmount.setText(String.valueOf("Amount Product: " + decimalFormat.format(amountNew) + " Đ"));
                finalViewHolder.btnQuantity.setText(String.valueOf(quantitiesNew));

                CartActivity.handlingValueCart();

                if (quantitiesNow >= 9) {
                    finalViewHolder.btnIncrease.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnDecrease.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnQuantity.setText(String.valueOf(quantitiesNew));
                } else {
                    finalViewHolder.btnIncrease.setVisibility(View.VISIBLE);
                    finalViewHolder.btnDecrease.setVisibility(View.VISIBLE);
                    finalViewHolder.btnQuantity.setText(String.valueOf(quantitiesNew));
                }
            }
        });

        viewHolder.btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantitiesNew = Integer.parseInt(finalViewHolder.btnQuantity.getText().toString()) - 1;

                int quantitiesNow = MainActivity.arrCart.get(position).getQuantity();
                long amountNow = MainActivity.arrCart.get(position).getAmount();

                MainActivity.arrCart.get(position).setQuantity(quantitiesNew);

                long amountNew = (amountNow * quantitiesNew) / quantitiesNow;
                MainActivity.arrCart.get(position).setAmount(amountNew);

                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.tvAmount.setText(String.valueOf("Amount Product: " + decimalFormat.format(amountNew) + " Đ"));
                finalViewHolder.btnQuantity.setText(String.valueOf(quantitiesNew));
                CartActivity.handlingValueCart();

                if (quantitiesNow < 2) {
                    finalViewHolder.btnDecrease.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnIncrease.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnQuantity.setText(String.valueOf(quantitiesNew));
                } else {
                    finalViewHolder.btnIncrease.setVisibility(View.VISIBLE);
                    finalViewHolder.btnDecrease.setVisibility(View.VISIBLE);
                    finalViewHolder.btnQuantity.setText(String.valueOf(quantitiesNew));
                }
            }
        });

        return view;
    }


}
