package com.example.phongphung.appdevicesshopping.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.phongphung.appdevicesshopping.R;
import com.example.phongphung.appdevicesshopping.adapter.LaptopAdapter;
import com.example.phongphung.appdevicesshopping.model.Product;
import com.example.phongphung.appdevicesshopping.utils.CheckConnection;
import com.example.phongphung.appdevicesshopping.utils.Key;
import com.example.phongphung.appdevicesshopping.utils.Notification;
import com.example.phongphung.appdevicesshopping.utils.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LaptopActivity extends AppCompatActivity {

    Toolbar toolbarLaptop;
    ListView listViewLaptop;
    LaptopAdapter laptopAdapter;
    ArrayList<Product> arrLaptop;
    int idTypeLaptop = 0;
    int page = 1;

    View footerView;
    boolean isLoading = false;
    MyHandler myHandler;

    boolean limitData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);

        initView();

        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            getIdProduct();
            actionToolBar();
            getData(page);
            loadMoreData();
        } else {
            Notification.showToast_Short(getApplicationContext(), "You should check connection!");
        }
    }

    private void loadMoreData() {

        listViewLaptop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ProductDetails.class);
                intent.putExtra(Key.KEY_PRODUCT_DETAILS, arrLaptop.get(position));
                startActivity(intent);
            }
        });

        listViewLaptop.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0 && isLoading == false && limitData == false) {
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }


    private void initView() {
        toolbarLaptop = findViewById(R.id.toolbarLaptop);
        listViewLaptop = findViewById(R.id.listViewLaptop);
        arrLaptop = new ArrayList<>();

        arrLaptop = new ArrayList<>();
        laptopAdapter = new LaptopAdapter(arrLaptop, getApplicationContext());
        listViewLaptop.setAdapter(laptopAdapter);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.item_process, null);

        myHandler = new MyHandler();
    }

    private void actionToolBar() {
        setSupportActionBar(toolbarLaptop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarLaptop.setNavigationOnClickListener(new View.OnClickListener() {
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
        switch (item.getItemId()) {
            case R.id.menu_cart:
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void getIdProduct() {
        idTypeLaptop = getIntent().getIntExtra(Key.KEY_ID_LAPTOP, -1);
    }


    private void getData(int page1) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String linkPhone = Server.linkPhone + String.valueOf(page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, linkPhone, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String name = "";
                int price = 0;
                String image = "";
                String desc = "";
                int idProductLaptop = 0;

                if (response != null && response.length() != 2) {
                    listViewLaptop.removeFooterView(footerView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            name = jsonObject.getString("name");
                            price = jsonObject.getInt("price");
                            image = jsonObject.getString("image");
                            desc = jsonObject.getString("description");
                            idProductLaptop = jsonObject.getInt("idType");

                            arrLaptop.add(new Product(id, name, price, image, desc, idProductLaptop));
                            laptopAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    limitData = true;
                    listViewLaptop.removeFooterView(footerView);
                    Notification.showToast_Short(getApplicationContext(), "Data displayed all");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Notification.showToast_Short(getApplicationContext(), error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> parram = new HashMap<String, String>();
                parram.put("idType", String.valueOf(idTypeLaptop));
                return parram;
            }
        };
        requestQueue.add(stringRequest);
    }

    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    listViewLaptop.addFooterView(footerView);
                    break;

                case 1:
                    getData(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public class ThreadData extends Thread {
        @Override
        public void run() {
            myHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = myHandler.obtainMessage(1); // Lien ket tiep tuc vs Handler
            myHandler.sendMessage(message);
            super.run();
        }
    }
}
