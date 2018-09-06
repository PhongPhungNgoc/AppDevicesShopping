package com.example.phongphung.appdevicesshopping.model;

public class Type {
    private int id;
    private String nameType;
    private String imageType;

    public Type() {
    }

    public Type(int id, String nameType, String imageType) {
        this.id = id;
        this.nameType = nameType;
        this.imageType = imageType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }
}
