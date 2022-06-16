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

import com.example.banhangonline.Adapter.ProductAdapter;
import com.example.banhangonline.Fragment.AddToCartFragment;
import com.example.banhangonline.Fragment.CartDialogFragment;
import com.example.banhangonline.Model.Cart;
import com.example.banhangonline.Model.Product;
import com.example.banhangonline.Model.ProductCart;
import com.example.banhangonline.Model.Store;
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

public class StoreDetailActivity extends AppCompatActivity implements ProductAdapter.OnProductItemClickListener, View.OnClickListener{

    TextView tvName, tvAddress, tvOpenHours,tvTotalPrices, tvTotalItems;
    ImageView ivCover;
    View layoutViewCart;
    RecyclerView rvProducts;
    ProductAdapter productAdapter;
    Store store;
    Product product;
    ArrayList<Product> products;
    CartRepository cartRepository;
    App app;
    FirebaseDatabase fDatabase;
    DatabaseReference dStore;
    FirebaseStorage fStorage;
    public final static HashMap<String, ProductCart> map = new HashMap<>();
    public static int order = 0;
    public static double price = 0;
    public static int quantity = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail);
        products = new ArrayList<>();
        tvName = findViewById(R.id.tvName);
        tvAddress = findViewById(R.id.tvAddress);
        tvOpenHours = findViewById(R.id.tvOpenHours);
        ivCover = findViewById(R.id.ivCover);

        rvProducts = findViewById(R.id.rvProducts);

        productAdapter = new ProductAdapter(products, this);
        rvProducts.setAdapter(productAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvProducts.setLayoutManager(layoutManager);
        rvProducts.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        cartRepository = new CartRepository(getApplication());
        fDatabase = FirebaseDatabase.getInstance();
        fStorage = FirebaseStorage.getInstance();


        Intent intent = getIntent();
        product = (Product) intent.getSerializableExtra("product");
        if(product != null) {
            onProductItemClick(product);
            dStore = fDatabase.getReference();
            Query query = dStore.child("stores").child(product.getResKey());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    store = snapshot.getValue(Store.class);
                    products.addAll(store.getMenu());
                    productAdapter.notifyDataSetChanged();
                    tvName.setText(store.name);
                    tvAddress.setText(store.address);
                    tvOpenHours.setText(store.getOpenHours());
                    StorageReference profileRef = fStorage.getReference().child("stores/covers/"+ store.getCover());
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
            store = (Store) intent.getSerializableExtra("store");
            products.addAll(store.getMenu());
            productAdapter.notifyDataSetChanged();
            tvName.setText(store.getName());
            tvAddress.setText(store.getAddress());
            tvOpenHours.setText(store.getOpenHours());
            Log.d("IJK", store.getCover());
            StorageReference profileRef = fStorage.getReference().child("stores/covers/"+ store.getCover());
            profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).into(ivCover);
                }
            });
        }
        layoutViewCart = findViewById(R.id.layoutViewCart);
        layoutViewCart.setOnClickListener(this);
        tvTotalPrices = findViewById(R.id.tvTotalPrices);
        tvTotalItems = findViewById(R.id.tvTotalItems);
        app = (App) getApplication();
        app.cart = new Cart();
        updateCart();
    }
    @Override
    public void onResume() {
        super.onResume();
        app.cart.products.clear();
        app.cart.calculateCart();
        ArrayList<com.example.banhangonline.Room.Cart> carts = new ArrayList<>();
        try {
            carts.addAll(cartRepository.getAllCarts());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
            for(int i=0;i<carts.size();i++){
                com.example.banhangonline.Room.Cart cart = carts.get(i);
                ProductCart productCart = new ProductCart(cart.getProductName(), cart.getProductImage(), cart.getProductPrice(), cart.getProductRate(), cart.getResKey(), cart.getProductKey(), cart.getQuantity(), (int) cart.getSum());
                app.cart.addProduct(productCart);
            }
            app.cart.calculateCart();
            updateCart();
        }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.layoutViewCart) {

            CartDialogFragment dialog = new CartDialogFragment(app.cart);
            dialog.show(getSupportFragmentManager(), "cart_dialog");
        }
    }

    @Override
    public void onProductItemClick(Product product) {
        int quantity = 1;
        double price = product.getPrice();

        ProductCart productCart = app.cart.getProduct(product.getProductKey());

        if(productCart == null)
            productCart = new ProductCart(product, quantity, price);

        AddToCartFragment dialog = new AddToCartFragment(productCart);
        dialog.show(getSupportFragmentManager(), "add_to_cart_dialog");
    }
    public void updateCart() {
        tvTotalItems.setText(String.valueOf(app.cart.getTotalItem()));
        tvTotalPrices.setText(String.valueOf(app.cart.getTotalPrice()));
        Log.d("ABC", map.size()+"");
    }
}