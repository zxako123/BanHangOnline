package com.example.banhangonline;

import android.app.Application;

import com.example.banhangonline.Model.Cart;

public class App extends Application{
    public Cart cart;
    public App(){
        cart = new Cart();
    }

    public App(Cart cart) {
        this.cart = cart;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
