package com.gutefind.mobile.ui.products;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gutefind.mobile.R;
import com.gutefind.mobile.databinding.FragmentProductListBinding;
import com.gutefind.mobile.util.Constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class FragmentProductList extends Fragment {

    private final Logger log = LoggerFactory.getLogger(FragmentProductList.class);

    private FragmentProductListBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProductListBinding.inflate(inflater, container, false);
        RecyclerView productListView = (RecyclerView) binding.productListView;

        ProductListAdapter productListAdapter = new ProductListAdapter(Constants.PRODUCT_LIST, this, getActivity());
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