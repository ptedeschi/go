package br.com.tedeschi.diapersgo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("Sku")
    @Expose
    private String sku;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Brand")
    @Expose
    private String brand;
    @SerializedName("Size")
    @Expose
    private String size;
    @SerializedName("Quantity")
    @Expose
    private Integer quantity;

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}