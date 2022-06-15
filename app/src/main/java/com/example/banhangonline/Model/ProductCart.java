package com.example.banhangonline.Model;

public class ProductCart extends Product {
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int quantity;
    public double sum;
    public Product product;

    public ProductCart() {
        super();
    }

    public ProductCart(String name, String image, int price, int rate, String resKey, String foodKey, int quantity, int sum) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.rate = rate;
        this.resKey = resKey;
        this.productKey = foodKey;
        this.quantity = quantity;
        this.sum = sum;
    }

    public ProductCart(Product product, int quantity, double sum){
        this.name = product.getName();
        this.image = product.getImage();
        this.price = product.getPrice();
        this.rate = product.getRate();
        this.resKey = product.getResKey();
        this.productKey = product.getProductKey();
        this.quantity = quantity;
        this.sum = sum;
    }
    public void increase() {
        quantity++;
        sum = price * quantity;
    }

    public void decrease() {
        if (quantity > 0) {
            quantity--;
            sum = price * quantity;
        }
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getSum() {
        return sum ;
    }

    public int getQuantity(){
        return quantity;
    }



    @Override
    public String toString() {
        return "ProductCart{" +
                "quantity=" + quantity +
                ", sum=" + sum +
                ", resKey='" + resKey + '\'' +
                '}';
    }
}
