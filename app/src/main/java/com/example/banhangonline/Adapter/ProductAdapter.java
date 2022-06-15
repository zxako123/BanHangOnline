package com.example.banhangonline.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banhangonline.Model.Product;
import com.example.banhangonline.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public  interface OnProductItemClickListener {
        void onProductItemClick(Product product);
    }

    public class ViewHolderProduct extends RecyclerView.ViewHolder{
        TextView tvName, tvRate,tvPrice;
        ImageView imageView;

        public ViewHolderProduct(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvRate = itemView.findViewById(R.id.tvRate);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            imageView = itemView.findViewById(R.id.ivImage);
        }
    }

    private List<Product> products;
    private OnProductItemClickListener productItemClickListener;

    public ProductAdapter(List<Product> products, OnProductItemClickListener productItemClickListener){
        this.products = products;
        this.productItemClickListener = productItemClickListener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_top_product, parent, false);
        return new ViewHolderProduct(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Product product = products.get(position);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();;
        ViewHolderProduct viewHolderProduct = (ViewHolderProduct) holder;
        StorageReference profileRef = storageReference.child("products/" + product.getImage());
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(viewHolderProduct.imageView);
            }
        });
        viewHolderProduct.tvName.setText(product.getName());
        viewHolderProduct.tvPrice.setText(product.getPrice()+"");
        viewHolderProduct.tvRate.setText("Rate: "+ product.getRate()+"");
        viewHolderProduct.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productItemClickListener.onProductItemClick(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
