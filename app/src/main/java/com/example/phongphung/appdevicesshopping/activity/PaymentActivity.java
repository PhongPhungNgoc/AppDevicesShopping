package com.example.phongphung.appdevicesshopping.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.phongphung.appdevicesshopping.R;
import com.example.phongphung.appdevicesshopping.utils.CheckConnection;
import com.example.phongphung.appdevicesshopping.utils.Notification;
import com.example.phongphung.appdevicesshopping.utils.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtName, edtPhone, edtEmail, edtAddress;
    private Button btnConfirm, btnCancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_customer);

        initView();
    }

    private void confirmOrder() {
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            final String name = edtName.getText().toString().trim();
            final String phone = edtPhone.getText().toString().trim();
            final String email = edtEmail.getText().toString().trim();
            final String address = edtEmail.getText().toString().trim();

            if (name.length() > 0 && phone.length() > 0 && email.length() > 0 && address.length() > 0) {
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.linkOrder, new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String idOrder) {
                        Log.d("id", idOrder);
                        if (Integer.parseInt(idOrder) > 0) {
                            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                            StringRequest request = new StringRequest(Request.Method.POST, Server.linkBill, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                     if(response.equals("1")){
                                         MainActivity.arrCart.clear();
                                         Notification.showToast_Short(getApplicationContext(), "Add Cart Success");
                                         Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                         startActivity(intent);
                                         Notification.showToast_Short(getApplicationContext(), "Continues Buying");
                                     }
                                     else {
                                         Notification.showToast_Short(getApplicationContext(), "Cart failure");
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
                                    JSONArray jsonArray = new JSONArray();
                                    for (int i = 0; i < MainActivity.arrCart.size(); i++) {
                                        JSONObject jsonObject = new JSONObject();
                                        try {
                                            jsonObject.put("idOrder", idOrder);
                                            jsonObject.put("idProduct", MainActivity.arrCart.get(i).getId());
                                            jsonObject.put("nameProduct", MainActivity.arrCart.get(i).getNameProduct());
                                            jsonObject.put("amount", MainActivity.arrCart.get(i).getAmount());
                                            jsonObject.put("quantity", MainActivity.arrCart.get(i).getQuantity());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        jsonArray.put(jsonObject);
                                    }
                                    HashMap<String, String> hashMap = new HashMap<String, String>();
                                    hashMap.put("json", jsonArray.toString());
                                    return hashMap;
                                }
                            };
                            queue.add(request);
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
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("nameCustomer", name);
                        hashMap.put("phoneCustomer", phone);
                        hashMap.put("emailCustomer", email);
                        hashMap.put("addressCustomer", address);
                        return hashMap;
                    }
                };
                requestQueue.add(stringRequest);
            } else {
                Notification.showToast_Short(getApplicationContext(), "Fields must not be empty");
            }
        } else {
            Notification.showToast_Short(getApplicationContext(), "You have check connection");
        }
    }

    private void initView() {
        edtName = findViewById(R.id.edt_nameCustomer);
        edtPhone = findViewById(R.id.edt_phoneCustomer);
        edtEmail = findViewById(R.id.edt_emailCustomer);
        edtAddress = findViewById(R.id.edt_addressCustomer);

        btnConfirm = findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(this);
        btnCancle = findViewById(R.id.btn_cancel);
        btnCancle.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                confirmOrder();
                break;

            case R.id.btn_cancel:
                finish();
                break;
        }
    }
}
