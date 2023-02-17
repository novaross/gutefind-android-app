package com.gutefind.mobile.ui.products;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gutefind.mobile.databinding.FragmentProductListBinding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class FragmentProductList extends Fragment {

    Logger log = LoggerFactory.getLogger(FragmentProductList.class);

    private FragmentProductListBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProductListBinding.inflate(inflater, container, false);
        RecyclerView productListView = (RecyclerView) binding.productListView;

        ProductListAdapter productListAdapter = new ProductListAdapter(createProductList(), this);
        productListView.setAdapter(productListAdapter);
        productListView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private List<Product> createProductList() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(1, "First Product", null));
        productList.add(new Product(2, "Second Product", null));
        productList.add(new Product(3, "Third Product", null));
        return productList;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}