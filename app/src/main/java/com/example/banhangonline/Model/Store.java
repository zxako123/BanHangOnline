package com.example.banhangonline.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Store implements Serializable {
    public String name;
    public String logo;
    public String cover;
    public String address;
    public String openHours;
    public ArrayList<Product> menu;
    public int rate;
    public String resKey;

    public Store(){
    }
    public Store(String name, String logo, String cover, String address, String openHours, ArrayList<Product> menu, int rate, String resKey) {
        this.name = name;
        this.logo = logo;
        this.cover = cover;
        this.address = address;
        this.openHours = openHours;
        this.menu = menu;
        this.rate = rate;
        this.resKey = resKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpenHours() {
        return openHours;
    }

    public void setOpenHours(String openHours) {
        this.openHours = openHours;
    }

    public ArrayList<Product> getMenu() {
        return menu;
    }

    public void setMenu(ArrayList<Product> menu) {
        this.menu = menu;
    }

    public int getRate() {
        return rate;
    }
    public String getResKey() {
        return resKey;
    }

    public void setResKey(String resKey) {
        this.resKey = resKey;
    }


    @Override
    public String toString() {
        return "Store{" +
                "name='" + name + '\'' +
                ", logo='" + logo + '\'' +
                ", cover='" + cover + '\'' +
                ", address='" + address + '\'' +
                ", openHours='" + openHours + '\'' +
                ", menu=" + menu +
                ", rate=" + rate +
                ", resKey='" + resKey + '\'' +
                '}';
    }

}
