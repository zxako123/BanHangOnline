package com.example.banhangonline.Fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.banhangonline.Adapter.ProductCartAdapter;
import com.example.banhangonline.Model.Cart;
import com.example.banhangonline.OrderActivity;
import com.example.banhangonline.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class CartDialogFragment extends DialogFragment implements View.OnClickListener {
    public TextView tvTotal;
    public RecyclerView rvProducts;
    public Cart cart;
    public ProductCartAdapter adapter;
    public Button btnPlaceOrder;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    /*
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartDialogFragment.
     */
     // TODO: Rename and change types and number of parameters
    public static CartDialogFragment newInstance(String param1, String param2) {
        CartDialogFragment fragment = new CartDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    public CartDialogFragment() {
        // Required empty public constructor
    }
    @SuppressLint("ValidFragment")
    public CartDialogFragment(Cart cart) {
        this.cart = cart;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTotal = view.findViewById(R.id.tvTotal);
        tvTotal.setText(cart.getTotalPrice()+"");
        rvProducts = view.findViewById(R.id.rvProducts);
        adapter = new ProductCartAdapter(new ArrayList<>(cart.products.values()));
        rvProducts.setAdapter(adapter);
        rvProducts.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        btnPlaceOrder = view.findViewById(R.id.btnPlaceOrder);
        btnPlaceOrder.setOnClickListener(this);

    }
    @Override
    public void onResume() {
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        getDialog().setCancelable(true);
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnPlaceOrder) {
            if (cart.getTotalItem() > 0) {
                Intent intent = new Intent(getContext(), OrderActivity.class);
                intent.putExtra("cart", cart);
                startActivity(intent);
                getActivity().finish();
                getDialog().dismiss();
            } else {
                getDialog().dismiss();
            }
        }


        int id = v.getId();
    }
    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}