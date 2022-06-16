package com.example.banhangonline.Model;

import java.io.Serializable;
import java.util.HashMap;

public class Cart implements Serializable {
    public HashMap<String, ProductCart> products;
    public double totalPrice;
    public int totalItem;

    public Cart(HashMap<String, ProductCart> productCart, double price, int quantity) {
        products = productCart;
        totalPrice = price;
        totalItem = quantity;
    }
    public Cart() {
        products = new HashMap<>();
        totalItem = 0;
        totalPrice = 0;
    }


    public void addProduct(ProductCart product) {
        products.put(product.getProductKey(), product);
    }

    public ProductCart getProduct(String key) {
        return products.get(key);
    }

    public void calculateCart() {
        totalPrice = 0;
        totalItem = 0;
        for (ProductCart productCart : products.values()) {
            totalPrice += (productCart.price * productCart.quantity);
            totalItem += 1;
        }
    }
    public String getTotalPrice() {
        return totalPrice + " VND";
    }

    public int getTotalItem() {
        return totalItem;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "products=" + products +
                ", totalPrice=" + totalPrice +
                ", totalItem=" + totalItem +
                '}';
    }
}

