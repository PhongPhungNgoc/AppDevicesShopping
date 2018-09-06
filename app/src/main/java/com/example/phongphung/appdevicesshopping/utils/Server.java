package com.example.phongphung.appdevicesshopping.utils;

public class Server {
//    public static String localhost = "10.255.151.128:8800";
    public static String localhost = "192.168.1.236:8800";
//    public static String localhost = "192.168.1.9:8800";

    public static String linkType = " http://" + localhost + "/appDevicesShopping/getType.php";
    public static String linkProductNew = " http://" + localhost + "/appDevicesShopping/getProductNew.php";
    public static String linkPhone = " http://" + localhost + "/appDevicesShopping/getProduct.php?page=";
    public static String linkOrder = " http://" + localhost + "/appDevicesShopping/informationCustomer.php";
    public static String linkBill = " http://" + localhost + "/appDevicesShopping/bill.php";
}
