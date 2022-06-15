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

import com.example.banhangonline.Model.Store;
import com.example.banhangonline.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public interface OnStoreItemClickListener {
        void onStoreItemClick(Store store);
    }

    public class ViewHolderStore extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress, tvOpenHour;
        ImageView ivImage;

        public ViewHolderStore(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvOpenHour = itemView.findViewById(R.id.tvOpenHour);
        }
    }

    public class ViewHolderTopStore extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress, tvRate;
        ImageView ivImage;

        public ViewHolderTopStore(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvRate = itemView.findViewById(R.id.tvRate);
        }
    }

    private List<Store> mStores;
    private OnStoreItemClickListener mListener;
    private int TYPE_LAYOUT;

    public StoreAdapter(List<Store> stores, OnStoreItemClickListener listener, int type_layout) {
        mStores = stores;
        mListener = listener;
        TYPE_LAYOUT = type_layout;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        if (TYPE_LAYOUT == 1) {
            View view = inflater.inflate(R.layout.row_store, parent, false);
            return new ViewHolderStore(view);
        } else {
            View view = inflater.inflate(R.layout.row_top_store, parent, false);
            return new ViewHolderTopStore(view);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Store store = mStores.get(position);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        if (TYPE_LAYOUT == 1) {
            ViewHolderStore viewHolderStore = (ViewHolderStore) holder;
            StorageReference profileRef = storageReference.child("restaurants/" + store.getLogo());
            profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).into(viewHolderStore.ivImage);
                }
            });
            viewHolderStore.tvName.setText(store.getName());
            viewHolderStore.tvAddress.setText(store.getAddress());
            viewHolderStore.tvOpenHour.setText(store.openHours);
            viewHolderStore.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onStoreItemClick(store);
                }
            });
        } else {
            ViewHolderTopStore viewHolderTopStore = (ViewHolderTopStore) holder;
            StorageReference profileRef = storageReference.child("restaurants/" + store.getLogo());
            profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).into(viewHolderTopStore.ivImage);
                }
            });
            viewHolderTopStore.tvName.setText(store.getName());
            viewHolderTopStore.tvRate.setText("Rate: ".concat(String.valueOf(store.rate)));
            viewHolderTopStore.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onStoreItemClick(store);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return mStores.size();
    }
}
