package com.example.phongphung.appdevicesshopping.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.phongphung.appdevicesshopping.R;
import com.example.phongphung.appdevicesshopping.adapter.ProductAdapter;
import com.example.phongphung.appdevicesshopping.adapter.SearchableAdapter;
import com.example.phongphung.appdevicesshopping.adapter.TypeAdapter;
import com.example.phongphung.appdevicesshopping.model.Cart;
import com.example.phongphung.appdevicesshopping.model.Product;
import com.example.phongphung.appdevicesshopping.model.Type;
import com.example.phongphung.appdevicesshopping.utils.CheckConnection;
import com.example.phongphung.appdevicesshopping.utils.Key;
import com.example.phongphung.appdevicesshopping.utils.Notification;
import com.example.phongphung.appdevicesshopping.utils.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewFlipper viewFlipper;
    private RecyclerView recyclerViewHome;
    private NavigationView navigationView;
    private ListView listViewHome;
    private DrawerLayout drawerLayout;
    private ArrayList<Type> arrTypes;
    private TypeAdapter typeAdapter;

    private SearchableAdapter mSearchableAdapter;

    private ArrayList<Product> arrProductNew;
    private ProductAdapter productAdapter;

    public static ArrayList<Cart> arrCart;

    int id;
    String nameType = "";
    String imageType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            actionBar();
            actionViewFlipper();
            getType();
            getProductNew();
            catchOnItemListView();
        } else {
            Notification.showToast_Short(getApplicationContext(), "You should check connection!");
            finish();
        }
    }

    private void catchOnItemListView() {
        listViewHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                        }else {
                            Notification.showToast_Short(getApplicationContext(),"You should check connection");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 1:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, PhoneActivity.class);
                            intent.putExtra(Key.KEY_ID_PHONE, arrTypes.get(position).getId());
                            startActivity(intent);
                        }else {
                            Notification.showToast_Short(getApplicationContext(),"You should check connection");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 2:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, LaptopActivity.class);
                            intent.putExtra(Key.KEY_ID_LAPTOP, arrTypes.get(position).getId());
                            startActivity(intent);
                        }else {
                            Notification.showToast_Short(getApplicationContext(),"You should check connection");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 3:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, TVActivity.class);
                            intent.putExtra("idType", arrTypes.get(position).getId());
                            startActivity(intent);
                        }else {
                            Notification.showToast_Short(getApplicationContext(),"You should check connection");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 4:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, ContactActivity.class);
                            startActivity(intent);
                        }else {
                            Notification.showToast_Short(getApplicationContext(),"You should check connection");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 5:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, InformationActivity.class);
                            startActivity(intent);
                        }else {
                            Notification.showToast_Short(getApplicationContext(),"You should check connection");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void getProductNew() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.linkProductNew, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int id = 0;
                    String name = "";
                    Integer price = 0;
                    String image = "";
                    String description = "";
                    int idType = 0;

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            name = jsonObject.getString("name");
                            price = jsonObject.getInt("price");
                            image = jsonObject.getString("image");
                            description = jsonObject.getString("description");
                            idType = jsonObject.getInt("idType");

                            arrProductNew.add(new Product(id, name, price, image, description, idType));
                            productAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Notification.showToast_Short(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void getType() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.linkType, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                arrTypes.add(0, new Type(0, "Home", "https://openclipart.org/image/2400px/svg_to_png/47197/home-icon6.png"));
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            nameType = jsonObject.getString("nameType");
                            imageType = jsonObject.getString("imageType");

                            arrTypes.add(new Type(id, nameType, imageType));
                            typeAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    arrTypes.add(new Type(4, "Contact", "http://silipgensan.com/wp-content/uploads/2017/07/icon-Contact.png"));
                    arrTypes.add(new Type(5, "Information", "https://cdn4.iconfinder.com/data/icons/Pretty_office_icon_part_2/256/personal-information.png"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Notification.showToast_Short(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void actionViewFlipper() {
        ArrayList<String> arrAdvertises = new ArrayList<>();
        arrAdvertises.add("https://cdn.tgdd.vn/Files/2017/03/09/958798/son-tung-oppo-f3_800x450.jpg");
        arrAdvertises.add("http://channel.mediacdn.vn/prupload/164/2017/05/img20170517124743625.jpg");
        arrAdvertises.add("https://hc.com.vn/media/catalog/product/cache/4/image/9df78eab33525d08d6e5fb8d27136e95/t/i/tivi-samsung-4k-ua49ku6100kxxv.jpg");
        arrAdvertises.add("http://godgetreview.com/wp-content/uploads/2018/03/hp-specter-x360-13-revi_godgetreview.com_-1.jpg");

        for (int i = 0; i < arrAdvertises.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(arrAdvertises.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);

        Animation animation_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);

        viewFlipper.setInAnimation(animation_in);
        viewFlipper.setOutAnimation(animation_out);
    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_home);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu, menu);

        MenuItem searchMenu = menu.findItem(R.id.app_bar_menu_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenu);

        final SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(R.id.search_src_text);

        // Create a new ArrayAdapter and add data to search auto complete object.
//        String dataArr[] = {"Apple" , "Amazon" , "Amd", "Microsoft", "Microwave", "MicroNews", "Intel", "Intelligence"};
//        ArrayAdapter<String> newsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, dataArr);
//
//        searchAutoComplete.setAdapter(newsAdapter);

        searchAutoComplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                System.out.println("Text ["+s+"]");

                mSearchableAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//        // Listen to search view item on click event.
//        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
//                String queryString=(String)adapterView.getItemAtPosition(itemIndex);
//                searchAutoComplete.setText("" + queryString);
//                Toast.makeText(MainActivity.this, "you clicked " + queryString, Toast.LENGTH_LONG).show();
//            }
//        });
//
//        // Below event is triggered when submit search query.
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
//                alertDialog.setMessage("Search keyword is " + query);
//                alertDialog.show();
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
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
        toolbar = findViewById(R.id.toolbarHome);
        viewFlipper = findViewById(R.id.viewFlipper);
        recyclerViewHome = findViewById(R.id.recycleView);
        navigationView = findViewById(R.id.navigationView);
        listViewHome = findViewById(R.id.listViewHome);
        drawerLayout = findViewById(R.id.drawableLayout);

        arrTypes = new ArrayList<>();
        typeAdapter = new TypeAdapter(arrTypes, getApplicationContext());
        listViewHome.setAdapter(typeAdapter);

        arrProductNew = new ArrayList<>();
        productAdapter = new ProductAdapter(arrProductNew, getApplicationContext());
        recyclerViewHome.setHasFixedSize(true);

        recyclerViewHome.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerViewHome.setAdapter(productAdapter);

        if(arrCart != null){

        }
        else {
            arrCart = new ArrayList<>();
        }

//        mSearchableAdapter = new SearchableAdapter()
    }
}
