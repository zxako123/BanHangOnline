package com.example.banhangonline.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.banhangonline.App;
import com.example.banhangonline.Room.Cart;
import com.example.banhangonline.Model.FoodBasket;
import com.example.banhangonline.R;
import com.example.banhangonline.RestaurantDetailActivity;
import com.example.banhangonline.Room.CartRepository;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddToBasketFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddToBasketFragment extends DialogFragment implements View.OnClickListener{

    TextView txtName, txtPrice,txtQuantity;
    Button btnBook;
    ImageView btnSubtract, btnAdd;
    FoodBasket foodBasket;
    int q = 0;
    double p = 0;
    boolean flag = false;
    String foodKey;

    App app;
    CartRepository cartRepository;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddToBasketFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddToBasketFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddToBasketFragment newInstance(String param1, String param2) {
        AddToBasketFragment fragment = new AddToBasketFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @SuppressLint("ValidFragment")
    public AddToBasketFragment(FoodBasket food) {
        this.foodBasket = food;
        Log.d("ABC", food.toString());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_to_basket, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtName = view.findViewById(R.id.tvName);
        txtPrice = view.findViewById(R.id.tvPrice);
        txtQuantity = view.findViewById(R.id.tvQuantity);
        btnAdd = view.findViewById(R.id.btnAdd);
        btnSubtract = view.findViewById(R.id.btnSubtract);
        btnBook = view.findViewById(R.id.btnLogout);
        btnBook.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnSubtract.setOnClickListener(this);


        cartRepository = new CartRepository(getActivity().getApplication());
        txtName.setText(foodBasket.getName());
        txtPrice.setText(foodBasket.getPrice() + " VND");
        updateStats();

        app = (App) getActivity().getApplication();


    }
    @Override
    public void onResume() {
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
        getDialog().setCancelable(true);
        super.onResume();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnSubtract:
                foodBasket.decrease();
                updateStats();
                break;
            case R.id.btnAdd:
                foodBasket.increase();
                updateStats();
                break;
            case R.id.btnLogout:
                if (foodBasket.quantity > 0) {
                    app.basket.addFood(foodBasket);
                    Cart cart = new Cart(foodBasket.getFoodKey(), foodBasket.getName(), foodBasket.getPrice(), foodBasket.getImage(), foodBasket.getRate(), foodBasket.getResKey(), foodBasket.getQuantity(), foodBasket.getSum());
                    cartRepository.insert(cart);
                    getDialog().dismiss();
                } else if(foodBasket.getQuantity() == 0){
                    cartRepository.deleteOneCart(foodBasket.getFoodKey());
                }

                ((RestaurantDetailActivity) getActivity()).onResume();
                ((RestaurantDetailActivity) getActivity()).updateBasket();
                break;
        }
    }

    private void updateStats() {
        if (foodBasket.getQuantity() > 0) {
            txtQuantity.setText(String.valueOf(foodBasket.getQuantity()));
            String add = getResources().getString(R.string.add_to_basket);
            btnBook.setText(add + " : " + foodBasket.getSum()+ " VND");
        } else if(foodBasket.getQuantity()==0){
            txtQuantity.setText(String.valueOf(foodBasket.getQuantity()));
            btnBook.setText(getResources().getString(R.string.back_to_menu));
        }
    }
}