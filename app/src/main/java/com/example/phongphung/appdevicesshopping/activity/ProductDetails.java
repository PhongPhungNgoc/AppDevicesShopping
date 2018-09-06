package com.example.phongphung.appdevicesshopping.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.phongphung.appdevicesshopping.R;
import com.example.phongphung.appdevicesshopping.model.Cart;
import com.example.phongphung.appdevicesshopping.model.Product;
import com.example.phongphung.appdevicesshopping.utils.Key;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductDetails extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbarDetails;
    private ImageView imgProductDetails;
    private TextView tvNameDetails, tvPriceDetails, tvDescDetails;
    private Button btnAddCart;
    private Spinner spinner;

    int id = 0;
    String nameDetails = "";
    int priceDetails = 0;
    String imgDetails = "";
    String descDetails = "";
    int idType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        initView();
        actionToolBar();
        getInformation();
        catchEventSpinner();

    }

    private void addCart() {
        int quantitiesCart = Integer.parseInt(spinner.getSelectedItem().toString());
        if (MainActivity.arrCart.size() > 0) {
            boolean exited = false;
            for (int i = 0; i < MainActivity.arrCart.size(); i++) {
                if (MainActivity.arrCart.get(i).getId() == id) {
                    MainActivity.arrCart.get(i).setQuantity(MainActivity.arrCart.get(i).getQuantity() + quantitiesCart);
                    if (MainActivity.arrCart.get(i).getQuantity() > 10) {
                        MainActivity.arrCart.get(i).setQuantity(10);
                    }
                    MainActivity.arrCart.get(i).setAmount(priceDetails * MainActivity.arrCart.get(i).getQuantity());
                    exited = true;
                }
            }
            if (exited == false) {
                long amount = quantitiesCart * priceDetails;
                MainActivity.arrCart.add(new Cart(id, nameDetails, amount, imgDetails, quantitiesCart));
            }
        } else {
            long amount = quantitiesCart * priceDetails;
            MainActivity.arrCart.add(new Cart(id, nameDetails, amount, imgDetails, quantitiesCart));
        }

        Intent intent = new Intent(getApplicationContext(), CartActivity.class);
        startActivity(intent);
    }

    private void catchEventSpinner() {
//        Integer[] quantity = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayList quantities = new ArrayList();
        for (int i = 1; i <= 10; i++) {
            quantities.add(i);
        }
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, quantities);
        spinner.setAdapter(arrayAdapter);
    }

    private void getInformation() {
        Product product = (Product) getIntent().getSerializableExtra(Key.KEY_PRODUCT_DETAILS);

        id = product.getId();
        nameDetails = product.getName();
        priceDetails = product.getPrice();
        imgDetails = product.getImage();
        descDetails = product.getDesciption();
        idType = product.getIdType();

        tvNameDetails.setText(nameDetails);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvPriceDetails.setText("Price: " + decimalFormat.format(product.getPrice()) + " đ");
        tvDescDetails.setText(descDetails);
        Glide.with(getApplicationContext()).load(imgDetails).placeholder(R.drawable.image).error(R.drawable.error).into(imgProductDetails);
    }

    private void actionToolBar() {
        setSupportActionBar(toolbarDetails);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarDetails.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_cart:
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        toolbarDetails = findViewById(R.id.toolbarProductDetails);
        imgProductDetails = findViewById(R.id.img_productDetails);
        tvNameDetails = findViewById(R.id.tv_nameProduct);
        tvPriceDetails = findViewById(R.id.tv_priceProduct);
        tvDescDetails = findViewById(R.id.tv_descProduct);
        spinner = findViewById(R.id.spinner_product);
        btnAddCart = findViewById(R.id.btn_addCart);
        btnAddCart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_addCart:
                addCart();
                break;
        }
    }
}
