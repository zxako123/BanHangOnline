package com.example.banhangonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.text.SimpleDateFormat;

import com.example.banhangonline.Adapter.ProductCartAdapter;
import com.example.banhangonline.Model.Cart;
import com.example.banhangonline.Model.Product;
import com.example.banhangonline.Model.ProductCart;
import com.example.banhangonline.Model.OrderFinished;
import com.example.banhangonline.Model.User;
import com.example.banhangonline.Room.CartRepository;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class OrderActivity extends AppCompatActivity implements OnMapReadyCallback {

    private TextView tvTotal, tvName, tvAddress, tvMobile;
    private RecyclerView rvProducts;
    private Cart cart;
    private ProductCartAdapter adapter;
    private Button btnPlaceOrder;
    GoogleMap map;
    FirebaseAuth fAuth;
    CartRepository cartRepository;
    FirebaseDatabase fDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.frgMaps);
        mapFragment.getMapAsync(this);
        tvAddress = findViewById(R.id.tvAddress);
        tvName = findViewById(R.id.tvName);
        tvMobile = findViewById(R.id.tvMobile);

        fAuth = FirebaseAuth.getInstance();
        fDatabase = FirebaseDatabase.getInstance();
        String userID = fAuth.getCurrentUser().getUid();
        fDatabase.getReference().child("users").child(userID).get()
                .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        LatLng latLngUser = new LatLng(user.getLatitude(), user.getLongitude());
                        MarkerOptions options = new MarkerOptions().position(latLngUser);
                        options.icon(BitmapDescriptorFactory.fromBitmap(
                                BitmapFactory.decodeResource(getResources(), R.drawable.ic_marker)));
                        map.addMarker(options);
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngUser, 16));
                        tvName.setText("Name: " + user.getFirstname() + " " + user.getLastname());
                        tvAddress.setText("Address: " + user.getAddress());
                        tvMobile.setText("Mobile: "+user.getMobile());
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        Intent intent = getIntent();
        cartRepository = new CartRepository(getApplication());
        if( intent.getSerializableExtra("cart") != null ){
            cart = (Cart) intent.getSerializableExtra("cart");
        }else {
            try {

                cart = new Cart();
                List<com.example.banhangonline.Room.Cart> carts = cartRepository.getAllCarts();
                for (com.example.banhangonline.Room.Cart cart : carts){
                    this.cart.addProduct(new ProductCart(new Product(cart.getProductName(),
                            cart.getProductImage(),
                            cart.getProductPrice(),
                            cart.getProductRate(),
                            cart.getResKey(),
                            cart.getProductKey()),
                            cart.getQuantity(),
                            cart.getSum()));
                            }
                cart.calculateCart();
                Log.d("ABC", cart.toString());

                }catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        tvTotal = findViewById(R.id.tvTotal);
        tvTotal.setText(cart.getTotalPrice()+"");
        rvProducts = findViewById(R.id.rvProduct);
        adapter = new ProductCartAdapter(new ArrayList<>(cart.products.values()));
        rvProducts.setAdapter(adapter);
        rvProducts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartRepository.deleteAll();
                String orderKey = fDatabase.getReference().child("orders").push().getKey();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String orderDate = sdf.format(System.currentTimeMillis());
                ArrayList<ProductCart> foodBaskets = new ArrayList<>();
//                for (int i = 0; i< cart.products.size();i++){
//                    foodBaskets.add(cart.getProduct(i+""));
//                }
                foodBaskets.addAll(cart.products.values());
                Log.d("DEF", foodBaskets.size()+"");
                OrderFinished orderFinished = new OrderFinished(orderKey, orderDate, cart.getTotalPrice(), 1, userID, foodBaskets);
                fDatabase.getReference().child("orders").child(userID).child(orderKey).setValue(orderFinished)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                               // StoreDetailActivity.map.clear();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
               App app = (App) getApplication();
               app.cart.products.clear();
               app.cart.calculateCart();
               Intent intent1 = new Intent(OrderActivity.this, MainActivity.class);
               startActivity(intent1);
               finish();
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
            if(googleMap!=null) {
                map = googleMap;
            }
    }
}