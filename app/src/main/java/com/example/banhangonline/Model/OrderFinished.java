package com.example.banhangonline.Model;

import java.util.ArrayList;

public class OrderFinished extends ProductCart {
    String orderID;
    String orderDate;
    String orderSum;
    int orderStatus;
    String userUID;
    ArrayList<ProductCart> productCarts;
    public OrderFinished() {
    }

    public OrderFinished(String orderID, String orderDate, String orderSum, int orderStatus, String userUID, ArrayList<ProductCart> productCarts) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.orderSum = orderSum;
        this.orderStatus = orderStatus;
        this.userUID = userUID;
        this.productCarts = productCarts;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderSum() {
        return orderSum;
    }

    public void setOrderSum(String orderSum) {
        this.orderSum = orderSum;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public ArrayList<ProductCart> getProductCarts() {
        return productCarts;
    }

    public void setProductCarts(ArrayList<ProductCart> productCarts) {
        this.productCarts = productCarts;
    }
}
