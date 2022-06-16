package com.example.banhangonline.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banhangonline.Model.ProductCart;
import com.example.banhangonline.R;

import java.util.List;

public class ProductCartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public class ViewHolderProductCart extends RecyclerView.ViewHolder{
        TextView tvName, tvPrice, tvQuantity, tvSum, tvCart;

        public ViewHolderProductCart(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.txtNameCart);
            tvPrice = itemView.findViewById(R.id.txtPriceCart);
            tvQuantity = itemView.findViewById(R.id.txtQuantity);
            tvSum = itemView.findViewById(R.id.txtSumCart);
            tvCart = itemView.findViewById(R.id.tvTitle);

        }
    }
    public ProductCartAdapter(){
    }

    private List<ProductCart> productCarts;

    public ProductCartAdapter(List<ProductCart> productCarts){
        this.productCarts = productCarts;

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_cart, parent, false);
        return new ViewHolderProductCart(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProductCart productCart =  productCarts.get(position);
        ViewHolderProductCart viewHolderProductCart = (ViewHolderProductCart) holder;
        viewHolderProductCart.tvName.setText(productCart.getName());
        viewHolderProductCart.tvQuantity.setText(String.valueOf(productCart.getQuantity()));
        viewHolderProductCart.tvPrice.setText(String.valueOf(productCart.getPrice()));
        viewHolderProductCart.tvSum.setText(String.valueOf(productCart.getSum()));
    }

    @Override
    public int getItemCount() {
        return productCarts.size();
    }
}
