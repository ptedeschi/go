package br.com.tedeschi.diapersgo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Deal {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("Price")
    @Expose
    private Double price;
    @SerializedName("UnityPrice")
    @Expose
    private Double unityPrice;
    @SerializedName("Comment")
    @Expose
    private String comment;
    @SerializedName("DateTime")
    @Expose
    private String dateTime;
    @SerializedName("Customer")
    @Expose
    private Customer customer;
    @SerializedName("Venue")
    @Expose
    private Venue venue;
    @SerializedName("Product")
    @Expose
    private Product product;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getUnityPrice() {
        return unityPrice;
    }

    public void setUnityPrice(Double unityPrice) {
        this.unityPrice = unityPrice;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}