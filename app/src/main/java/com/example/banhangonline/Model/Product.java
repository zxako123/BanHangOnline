package com.example.banhangonline.Model;

import java.io.Serializable;

public class Product implements Serializable, Comparable<Product> {
    String name;
    String image;
    int price;
    int rate;
    String resKey;
    String productKey;

    public Product() {
    }

    public Product(String name, String image, int price, int rate, String resKey, String productKey) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.rate = rate;
        this.resKey = resKey;
        this.productKey = productKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getResKey() {
        return resKey;
    }

    public void setResKey(String resKey) {
        this.resKey = resKey;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", image=" + image +
                ", price=" + price +
                ", rate=" + rate +
                ", resKey='" + resKey + '\'' +
                ", productKey='" + productKey + '\'' +
                '}';
    }

    @Override
    public int compareTo(Product product) {
        return Integer.compare(product.rate, rate);
    }
}