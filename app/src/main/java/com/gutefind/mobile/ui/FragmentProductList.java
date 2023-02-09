package com.gutefind.mobile.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gutefind.mobile.R;
import com.gutefind.mobile.databinding.FragmentProductListBinding;

import java.util.ArrayList;
import java.util.List;

public class FragmentProductList extends Fragment {

    private FragmentProductListBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentProductListBinding.inflate(inflater, container, false);

        RecyclerView productListView = (RecyclerView) binding.productListView;

        List<Product> productList = new ArrayList<>();
        productList.add(new Product(1, "First Product", null));
        productList.add(new Product(2, "Second Product", null));

        ProductListAdapter productListAdapter = new ProductListAdapter(productList, this);
        productListView.setAdapter(productListAdapter);
        productListView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}