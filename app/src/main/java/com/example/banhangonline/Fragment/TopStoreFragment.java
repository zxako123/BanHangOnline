package com.example.banhangonline.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.banhangonline.Adapter.StoreAdapter;
import com.example.banhangonline.Model.Store;
import com.example.banhangonline.R;
import com.example.banhangonline.StoreDetailActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TopStoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopStoreFragment extends Fragment implements StoreAdapter.OnStoreItemClickListener {

    FirebaseDatabase firebaseDatabase;
    StoreAdapter storeAdapter;
    ArrayList<Store> topStores;
    RecyclerView rvTopStore;
    DatabaseReference reference;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TopStoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TopStoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TopStoreFragment newInstance(String param1, String param2) {
        TopStoreFragment fragment = new TopStoreFragment();
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
        topStores = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_store, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvTopStore = view.findViewById(R.id.rvTopStore);
        storeAdapter = new StoreAdapter(topStores, this, 0);
        rvTopStore.setAdapter(storeAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        rvTopStore.setLayoutManager(layoutManager);
        rvTopStore.addItemDecoration(new DividerItemDecoration(getContext(), GridLayoutManager.VERTICAL));
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();

        Query query = reference.child("stores").orderByChild("rate").limitToLast(5);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                topStores.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Store store = dataSnapshot.getValue(Store.class);
                    topStores.add(store);
                }
                storeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void onStoreItemClick(Store store) {
        Intent intent = new Intent(getContext(), StoreDetailActivity.class);
        intent.putExtra("store", store);
        startActivity(intent);
    }
}