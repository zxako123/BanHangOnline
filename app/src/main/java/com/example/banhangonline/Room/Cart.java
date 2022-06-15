package com.example.banhangonline.Room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Cart {
    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "productKey")
    String productKey;
    @ColumnInfo(name = "productName")
    String productName;
    @ColumnInfo(name = "productPrice")
    int productPrice;
    @ColumnInfo(name= "image")
    String productImage;
    @ColumnInfo(name = "rate")
    int productRate;
    @ColumnInfo(name = "reskey")
    String resKey;
    @ColumnInfo(name = "quantity")
    int quantity;
    @ColumnInfo(name = "sum")
    double sum;
    @Ignore
    public Cart() {
    }

    public Cart(String productKey, String productName, int productPrice, String productImage, int productRate, String resKey, int quantity, double sum) {
        this.productKey = productKey;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.productRate = productRate;
        this.resKey = resKey;
        this.quantity = quantity;
        this.sum = sum;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getProductRate() {
        return productRate;
    }
    public void setProductRate(int productRate) {
        this.productRate = productRate;
    }

    public String getResKey() {
        return resKey;
    }

    public void setResKey(String resKey) {
        this.resKey = resKey;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
}

