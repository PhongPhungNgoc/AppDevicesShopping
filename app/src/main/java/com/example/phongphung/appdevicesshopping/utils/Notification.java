package com.example.phongphung.appdevicesshopping.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

public class Notification {
    public static void showToast_Short(Context context, String notification){
        Toast.makeText(context, notification, Toast.LENGTH_SHORT).show();
    }
}
