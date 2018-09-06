package com.example.phongphung.appdevicesshopping.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.phongphung.appdevicesshopping.R;
import com.example.phongphung.appdevicesshopping.adapter.CartAdapter;
import com.example.phongphung.appdevicesshopping.utils.Notification;

import java.text.DecimalFormat;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbarCart;
    private ListView lvCart;
    private static TextView tvValuesCart;
    private TextView tvNotification;
    private Button btnPay, btnContinues;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initView();
        actionToolbar();
        checkCart();
        handlingValueCart();
        catchOnItemListView();
    }

    private void catchOnItemListView() {
        lvCart.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                builder.setTitle("Delete Product");
                builder.setMessage("Do you want to delete this product ?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (MainActivity.arrCart.size() <= 0) {
                            tvNotification.setVisibility(View.VISIBLE);
                        } else {
                            MainActivity.arrCart.remove(position);
                            cartAdapter.notifyDataSetChanged();
                            handlingValueCart();

                            if (MainActivity.arrCart.size() <= 0) {
                                tvNotification.setVisibility(View.VISIBLE);
                            } else {
                                tvNotification.setVisibility(View.INVISIBLE);
                                cartAdapter.notifyDataSetChanged();
                                handlingValueCart();
                            }
                        }
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cartAdapter.notifyDataSetChanged();
                        handlingValueCart();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void handlingValueCart() {
        long valuesCart = 0;
        for (int i = 0; i < MainActivity.arrCart.size(); i++) {
            valuesCart += MainActivity.arrCart.get(i).getAmount();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvValuesCart.setText(String.valueOf(decimalFormat.format(valuesCart) + " Đ"));
    }

    private void checkCart() {
        if (MainActivity.arrCart.size() <= 0) {
            cartAdapter.notifyDataSetChanged();
            tvNotification.setVisibility(View.VISIBLE);
            lvCart.setVisibility(View.INVISIBLE);
        } else {
            cartAdapter.notifyDataSetChanged();
            tvNotification.setVisibility(View.INVISIBLE);
            lvCart.setVisibility(View.VISIBLE);
        }
    }

    private void actionToolbar() {
        setSupportActionBar(toolbarCart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarCart.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        toolbarCart = findViewById(R.id.toolbarCart);
        tvNotification = findViewById(R.id.tv_notificationCart);
        tvValuesCart = findViewById(R.id.tv_values);
        btnPay = findViewById(R.id.btn_payCart);
        btnPay.setOnClickListener(this);

        btnContinues = findViewById(R.id.btn_continueShopping);
        btnContinues.setOnClickListener(this);

        lvCart = findViewById(R.id.listViewCart);
        cartAdapter = new CartAdapter(MainActivity.arrCart, getApplicationContext());
        lvCart.setAdapter(cartAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_continueShopping:
                Intent intentContinues = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentContinues);
                break;

            case R.id.btn_payCart:
                if (MainActivity.arrCart.size() > 0) {
                    Intent intentPay = new Intent(getApplicationContext(), PaymentActivity.class);
                    startActivity(intentPay);
                } else {
                    Notification.showToast_Short(getApplicationContext(), "Cart empty. Can't excute transaction");
                }
                break;
        }
    }
}
