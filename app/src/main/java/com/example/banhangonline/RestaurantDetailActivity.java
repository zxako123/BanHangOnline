package com.example.banhangonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.banhangonline.Adapter.FoodAdapter;
import com.example.banhangonline.Fragment.AddToBasketFragment;
import com.example.banhangonline.Fragment.BasketDialogFragment;
import com.example.banhangonline.Model.Basket;
import com.example.banhangonline.Room.Cart;
import com.example.banhangonline.Model.Food;
import com.example.banhangonline.Model.FoodBasket;
import com.example.banhangonline.Model.Restaurant;
import com.example.banhangonline.Room.CartRepository;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class RestaurantDetailActivity extends AppCompatActivity implements FoodAdapter.OnFoodItemClickListener, View.OnClickListener{

    TextView tvName, tvAddress, tvOpenHours,tvTotalPrices, tvTotalItems;
    ImageView ivCover;
    View layoutViewBasket;
    RecyclerView rvFoods;
    FoodAdapter foodAdapter;
    Restaurant restaurant;
    Food food;
    ArrayList<Food> foods;
    CartRepository cartRepository;
    App app;
    FirebaseDatabase fDatabase;
    DatabaseReference dRestaurant;
    FirebaseStorage fStorage;
    public final static HashMap<String, FoodBasket> map = new HashMap<>();
    public static int order = 0;
    public static double price = 0;
    public static int quantity = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);
        foods = new ArrayList<>();
        tvName = findViewById(R.id.tvName);
        tvAddress = findViewById(R.id.tvAddress);
        tvOpenHours = findViewById(R.id.tvOpenHours);
        ivCover = findViewById(R.id.ivCover);

        rvFoods = findViewById(R.id.rvFoods);

        foodAdapter = new FoodAdapter(foods, this);
        rvFoods.setAdapter(foodAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvFoods.setLayoutManager(layoutManager);
        rvFoods.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        cartRepository = new CartRepository(getApplication());
        fDatabase = FirebaseDatabase.getInstance();
        fStorage = FirebaseStorage.getInstance();


        Intent intent = getIntent();
        food = (Food) intent.getSerializableExtra("food");
        if(food != null) {
            onFoodItemClick(food);
            dRestaurant = fDatabase.getReference();
            Query query = dRestaurant.child("restaurants").child(food.getResKey());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    restaurant = snapshot.getValue(Restaurant.class);
                    foods.addAll(restaurant.getMenu());
                    foodAdapter.notifyDataSetChanged();
                    tvName.setText(restaurant.name);
                    tvAddress.setText(restaurant.address);
                    tvOpenHours.setText(restaurant.getOpenHours());
                    StorageReference profileRef = fStorage.getReference().child("restaurants/covers/"+ restaurant.getCover());
                    profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.get().load(uri).into(ivCover);
                        }
                    });
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else{
            restaurant = (Restaurant) intent.getSerializableExtra("restaurant");
            foods.addAll(restaurant.getMenu());
            foodAdapter.notifyDataSetChanged();
            tvName.setText(restaurant.getName());
            tvAddress.setText(restaurant.getAddress());
            tvOpenHours.setText(restaurant.getOpenHours());
            Log.d("IJK", restaurant.getCover());
            StorageReference profileRef = fStorage.getReference().child("restaurants/covers/"+ restaurant.getCover());
            profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).into(ivCover);
                }
            });
        }
        layoutViewBasket = findViewById(R.id.layoutViewBasket);
        layoutViewBasket.setOnClickListener(this);
        tvTotalPrices = findViewById(R.id.tvTotalPrices);
        tvTotalItems = findViewById(R.id.tvTotalItems);
        app = (App) getApplication();
        app.basket = new Basket();
        updateBasket();
    }
    @Override
    public void onResume() {
        super.onResume();
        app.basket.foods.clear();
        app.basket.calculateBasket();
        ArrayList<Cart> carts = new ArrayList<>();
        try {
            carts.addAll(cartRepository.getAllCarts());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
            for(int i=0;i<carts.size();i++){
                Cart cart = carts.get(i);
                FoodBasket foodBasket = new FoodBasket(cart.getFoodName(), cart.getFoodImage(), cart.getFoodPrice(), cart.getFoodRate(), cart.getResKey(), cart.getFoodKey(), cart.getQuantity(), (int) cart.getSum());
                app.basket.addFood(foodBasket);
            }
            app.basket.calculateBasket();
            updateBasket();
        }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.layoutViewBasket) {

            BasketDialogFragment dialog = new BasketDialogFragment(app.basket);
            dialog.show(getSupportFragmentManager(), "basket_dialog");
        }
    }

    @Override
    public void onFoodItemClick(Food food) {
        int quantity = 1;
        double price = food.getPrice();

        FoodBasket foodBasket = app.basket.getFood(food.getFoodKey());

        if(foodBasket == null)
            foodBasket = new FoodBasket(food, quantity, price);

        AddToBasketFragment dialog = new AddToBasketFragment(foodBasket);
        dialog.show(getSupportFragmentManager(), "add_to_basket_dialog");
    }
    public void updateBasket() {
        tvTotalItems.setText(String.valueOf(app.basket.getTotalItem()));
        tvTotalPrices.setText(String.valueOf(app.basket.getTotalPrice()));
        Log.d("ABC", map.size()+"");
    }
}