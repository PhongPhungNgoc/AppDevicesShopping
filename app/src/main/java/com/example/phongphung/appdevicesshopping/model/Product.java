package com.example.phongphung.appdevicesshopping.model;

import android.widget.Filter;

import java.io.Serializable;

public class Product extends Filter implements Serializable{
    private int id;
    private String name;
    private int price;
    private String image;
    private String desciption;
    private int idType;

    public Product() {
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        return null;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

    }

    public Product(int id, String name, int price, String image, String desciption, int idType) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.desciption = desciption;
        this.idType = idType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }
}
