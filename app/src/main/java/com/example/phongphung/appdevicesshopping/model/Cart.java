package com.example.phongphung.appdevicesshopping.model;

public class Cart {
    private int id;
    private String nameProduct;
    private long amount;
    private String imgProduct;
    private int quantity;

    public Cart(int id, String nameProduct, long amount, String imgProduct, int quantity) {
        this.id = id;
        this.nameProduct = nameProduct;
        this.amount = amount;
        this.imgProduct = imgProduct;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getImgProduct() {
        return imgProduct;
    }

    public void setImgProduct(String imgProduct) {
        this.imgProduct = imgProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
