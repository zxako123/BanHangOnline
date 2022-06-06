package com.example.banhangonline;

import android.app.Application;

import com.example.banhangonline.Model.Basket;

public class App extends Application{
    public Basket basket;
    public App(){
        basket = new Basket();
    }

    public App(Basket basket) {
        this.basket = basket;
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }
}
